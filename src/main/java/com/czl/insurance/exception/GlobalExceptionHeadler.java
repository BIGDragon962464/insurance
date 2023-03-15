package com.czl.insurance.exception;

import com.czl.insurance.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常类
 */
@ControllerAdvice
public class GlobalExceptionHeadler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result headle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
