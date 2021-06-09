package com.yuham.user.web;

import com.yuham.user.exception.ErrorResponse;
import com.yuham.user.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.SignatureException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author yuhan
 * @date 05.06.2021 - 13:13
 * @purpose
 */
@Hidden
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse badRequest(MethodArgumentNotValidException exception) {
        String validationErrors = prepareValidationErrors(exception.getBindingResult().getFieldErrors());
        if (logger.isDebugEnabled()) {
            logger.debug("Bad Request: {}", validationErrors);
        }
        return new ErrorResponse(validationErrors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFound(EntityNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse conflict(NotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleException(RuntimeException exception) {
        logger.error("", exception);
        return new ErrorResponse(exception.getMessage());
    }

    private String prepareValidationErrors(List<FieldError> errors) {
        StringBuilder str = new StringBuilder();
        for (FieldError error : errors) {
            String field = error.getField();
            String defaultMessage = error.getDefaultMessage();
            str.append(String.format("Field %s has wrong value: [%s]\n", field, defaultMessage));
        }
        return str.toString();
    }

//    @ExceptionHandler(value = { SignatureException.class })
//    @ResponseBody
//    public ResultBO<?> authorizationException(SignatureException e){
//        return ResultTool.error(new ExceptionInfoBO(1008,e.getMessage()));
//    }


}
