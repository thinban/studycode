package com.example.thinbanrest.aop;

import com.example.thinbanrest.core.BizException;
import com.example.thinbanrest.core.ResultMap;
import com.example.thinbanrest.core.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 统一异常处理类
 *
 * @author thinban
 * @date 2017-12-24
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionGet(Exception e) {
        ResultMap resultMap = new ResultMap();
        if (e instanceof BizException) {
            resultMap = ResultUtil.error(e.getMessage());
        }
        log.error("系统异常:{}", e.getMessage());
        //请求参数校验
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = c.getBindingResult();
            if (bindingResult.hasFieldErrors()) {
                List<FieldError> errors = bindingResult.getFieldErrors();
                StringBuilder sb = new StringBuilder();
                for (FieldError error : errors) {
                    sb.append(error.getDefaultMessage()).append("\r\n");
                }
                resultMap = ResultUtil.error(sb.toString());
            }
        }
        return resultMap;
    }
}
