package com.example.thinbanrest.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.thinbanrest.core.BizException;
import com.example.thinbanrest.utils.AppAesUtils;
import com.example.thinbanrest.utils.ServletContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) {
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                ApiParam param = JSON.parseObject(inputMessage.getBody(), ApiParam.class);
                boolean isDecrypt = parameter.getMethod().isAnnotationPresent(Decrypt.class);
                boolean debug = "free".equals(param.getSign());
                //无需加密或者调试
                if (!isDecrypt || debug) {
                    EncryptResponseBodyAdvice.setEncryptStatus(false);
                    setLog(inputMessage, parameter, param.getBiz_content());
                    return IOUtils.toInputStream(param.getBiz_content(), EncryptProperties.charset);
                }
                //业务参数解密
                String decryptBody = AppAesUtils.aesDecrypt(param.getBiz_content());
                long now = System.currentTimeMillis();
                long sec = 3 * 60 * 1000L;
                //判断请求是否过期
                Long timestamp = param.getTimestamp();
                if ((timestamp > now + sec || timestamp < now - sec)) {
                    throw new BizException("请求已过期");
                }
                //验证签名
                if (!param.validateSign()) {
                    throw new BizException("签名参数有误");
                }
                setLog(inputMessage, parameter, decryptBody);
                return IOUtils.toInputStream(decryptBody, EncryptProperties.charset);
            }

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };

    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    private void setLog(HttpInputMessage inputMessage, MethodParameter parameter, String body) {
        LogIt logIt = parameter.getMethod().getAnnotation(LogIt.class);
        if (logIt != null) {
            JSONObject logEntity = new JSONObject().fluentPut("url", ServletContextHolder.getUrl());
            if (!logIt.noHeader()) {
                logEntity.put("header", inputMessage.getHeaders());
            }
            logEntity.put("body", body);
            EncryptResponseBodyAdvice.setLogLocal(logEntity);
        }
    }
}
