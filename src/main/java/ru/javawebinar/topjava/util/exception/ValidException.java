package ru.javawebinar.topjava.util.exception;

import org.springframework.validation.BindingResult;

public class ValidException extends RuntimeException {
    public ValidException(BindingResult result) {
        super(getMessage(result));
    }

    public static String getMessage(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        return sb.toString();
    }
}
