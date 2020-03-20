package com.iyushchuk.tictactoe.controllers;

import com.iyushchuk.tictactoe.common.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            FaultyMoveException.class,
            IllegalMoveException.class,
            PlayerAlreadyExistsException.class,
            UnsupportedGridException.class
    })
    protected ResponseEntity<Object> handleBadRequest(ApplicationException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {PlayerDoesNotExistException.class, GameDoesNotExistException.class})
    protected ResponseEntity<Object> handleEntityNotFound(ApplicationException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<Object> handleMessUp(ApplicationException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {NotAuthorizedToPlayGameException.class})
    protected ResponseEntity<Object> handleNotAuthorized(ApplicationException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
