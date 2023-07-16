package com.example.ruijidemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author lyz
 * 全局异常处理
 * @DATE 2023-07-03 18:00
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private R<String>  exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] msgs = ex.getMessage().split(" ");
            String msg = msgs[2] + "已存在";
            return R.error(msg);
        }
        return R.error("操作失败，未知错误！");
    }
    @ExceptionHandler(CustomException.class)
    private R<String>  exceptionHandler(CustomException ex){
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }

}
