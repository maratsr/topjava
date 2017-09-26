package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(MethodArgumentNotValidException.class) // T
    @ResponseBody
    public ErrorInfo bindError(HttpServletRequest req, MethodArgumentNotValidException be) {
        System.out.println("-------------------MethodArgumentNotValidException");
        System.out.println(be.getBindingResult().getAllErrors().get(0).toString());
        //LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        return getBindErrorInfo(req, be.getBindingResult().getFieldErrors());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(BindException.class) // T
    @ResponseBody
    public ErrorInfo validateError(HttpServletRequest req, BindException be) {
        //LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        System.out.println("---------------BindException");
        return getBindErrorInfo(req, be.getFieldErrors());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), rootCause);
    }

    private static ErrorInfo getBindErrorInfo(HttpServletRequest req, List<FieldError> errorList) {
        StringJoiner joiner = new StringJoiner("<br>");
        errorList.forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (!msg.startsWith(fe.getField())) {
                        msg = fe.getField() + ':' + msg;
                    }
                    joiner.add(msg+"\n");
                });
        return new ErrorInfo(req.getRequestURL(), "Validation Error", joiner.toString());
    }
}