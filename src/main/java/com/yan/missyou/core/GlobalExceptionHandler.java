/**
 * @ClassName GlobalExceptionHandler
 * @description: TODO
 * @author Argus
 * @Date 2020/3/10 12:36
 * @Version V1.0
 */
package com.yan.missyou.core;

import com.yan.missyou.core.configration.ExceptionCodeConfigration;
import com.yan.missyou.exception.http.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * SB全局异常处理器
 * // todo 参数校验异常
 * @author Argus
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ExceptionCodeConfigration codeConfigration;

    /**
     * 抛出基类异常会跑到这个方法
     * 本方法处理基类Exception
     * ResponseBody返回json格式
     * ResponseStatus 设置返回ResponseStatus 为500 修改http状态码
     *
     * @param req 获取当前请求url
     * @param e   获取异常信息
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        log.error("路由：" + req.getMethod() + " " + url + "：------------------>捕捉到服务器未知异常", e);
        // 需要添加getter作为读取器 否则spring序列化的时候无法拿到数据
        return new UnifyResponse(999, "服务器未知异常", method + " " + url);
    }

    /**
     * 抛出自定义异常会跑到这个方法
     * 处理自定义异常
     *  返回用ResponseEntity对象包装http status code和UnifyResponse一起返回，这种相比于第一种更灵活
     * @param req
     * @param e
     * @throws Exception
     */
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) throws Exception {
        String url = req.getRequestURI();
        String method = req.getMethod();
        log.error("路由：" + req.getMethod() + " " + url + "：------------------>捕捉到自定义异常", e);
        HttpHeaders headers = new HttpHeaders();
        // @ResponseBody 因为没有使用注解需要设置 使用json格式返回
        headers.setContentType(MediaType.APPLICATION_JSON);
        // ResponseStatus 定义返回状态码
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        // 定义返回内容 e.getCode()自定义异常定义的code值 通过抛出来定义的code获取
        UnifyResponse unifyResponse = new UnifyResponse(e.getCode(), codeConfigration.getMessages(e.getCode()), method + " " + url);
        // 设置返回对象
        ResponseEntity<UnifyResponse> responseEntity = new ResponseEntity<>(unifyResponse, headers, httpStatus);
        return responseEntity;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String url = req.getRequestURI();
        String method = req.getMethod();
        log.error("路由：" + req.getMethod() + " " + url + "：------------------>捕捉到参数校验异常", e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorsMessages(errors);
        System.out.println(message);
        UnifyResponse unifyResponse = new UnifyResponse(10001, message, method + " " + url);
        return unifyResponse;
    }

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleParamValidation(HttpServletRequest req, ConstraintViolationException e){
        String url = req.getRequestURI();
        String method = req.getMethod();
        log.error("路由：" + req.getMethod() + " " + url + "：------------------>捕捉到路由参数异常", e);
        StringBuilder errorMsg = new StringBuilder();
        System.out.println(e); //断点查看内部的属性
        for(ConstraintViolation error:e.getConstraintViolations()) {
            String msg = error.getMessage();
            String m = error.getPropertyPath().toString();
            System.out.println(m);
            String name = m.split("[.]")[1]; //getSpuDetail.id 截取
            System.out.println(name);
            ConstraintViolation violation = error;//可打断点看到效果
//            System.out.println(violation.getMessage());
            // 最后项目完结 需要更改提示。
            errorMsg.append(name).append(" ").append(msg).append(";");
        }
        String esg = errorMsg.toString();
//        System.out.println(esg);
        String message = e.getMessage();
        return new UnifyResponse(10001, message, method + " " + url);
    }

    private String formatAllErrorsMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        // 遍历集合
        errors.forEach(error ->
                //拼接错误 信息
                errorMsg.append(error.getDefaultMessage()).append(";"));
        return errorMsg.toString();
    }



}
