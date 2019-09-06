package ru.filippov.neatvue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /*@ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity vehicleNotFound(UserNotFoundException ex, WebRequest request) {
        log.debug("handling UserNotFoundException...");
        return null;
    }*/

}
