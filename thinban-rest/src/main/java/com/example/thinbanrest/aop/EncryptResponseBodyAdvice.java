package com.example.thinbanrest.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.thinbanrest.utils.AppAesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<Boolean>();
    private static ThreadLocal<JSONObject> logLocal = new ThreadLocal<JSONObject>();

    public static void setEncryptStatus(boolean status) {
        encryptLocal.set(status);
    }

    public static void setLogLocal(JSONObject jo) {
        logLocal.set(jo);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 可以通过调用EncryptResponseBodyAdvice.setEncryptStatus(false);来动态设置不加密操作
        Boolean status = encryptLocal.get();
        String content = JSON.toJSONString(body);
        if (status == null || status) {
            long startTime = System.currentTimeMillis();
            boolean encrypt = false;
            if (returnType.getMethod().isAnnotationPresent(Encrypt.class) && !EncryptProperties.debug) {
                encrypt = true;
            }
            if (encrypt) {
                try {
                    if (!StringUtils.hasText(EncryptProperties.key)) {
                        throw new NullPointerException("请配置spring.encrypt.key");
                    }
                    String result = AppAesUtils.aesEncrypt(content);
                    long endTime = System.currentTimeMillis();
                    log.debug("Encrypt Time:" + (endTime - startTime));
                    return result;
                } catch (Exception e) {
                    log.error("加密数据异常", e);
                }
            }
        }
        encryptLocal.remove();
        handleLog(content);
        logLocal.remove();
        return body;
    }

    public static void handleLog(String content) {
        //处理日志
        JSONObject logEntity = logLocal.get();
        if (logEntity != null) {
            logEntity.put("response", content);
            log.info("处理请求:{}", logEntity);
        }
    }

}
