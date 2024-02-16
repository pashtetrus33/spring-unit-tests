package com.example.springdemotest.controller;

import com.example.springdemotest.exceptions.CredentialsAreWrongException;
import com.example.springdemotest.exceptions.SessionNotFoundException;
import com.example.springdemotest.exceptions.UserIsExistsException;
import com.example.springdemotest.exceptions.UserNotFoundException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Класс перехватывающий исключения
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Обработка исключения при отсутствии пользователя.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerUserNotFound(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * Обработка исключения при наличии пользователя.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(UserIsExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerUserIsFound(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Обработка исключения при ошибке аутентификации пользователя.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(CredentialsAreWrongException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerCredentialsAreWrong(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Обработка исключения при отсутствии сессии.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(SessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerSessionNotFound(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения при отсутствии некорректном запросе.
     *
     * @param ex обьект исключения
     * @return ответ с ошибкой
     */
    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConversion(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}