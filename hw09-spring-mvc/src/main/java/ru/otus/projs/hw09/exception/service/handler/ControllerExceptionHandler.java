package ru.otus.projs.hw09.exception.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler{

    private static final String ERROR_FORMAT = "%s: %s";

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        errorMessage = (throwable.getCause() != null) ?
                String.format(ERROR_FORMAT,errorMessage, throwable.getCause().getMessage()) : "";
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
