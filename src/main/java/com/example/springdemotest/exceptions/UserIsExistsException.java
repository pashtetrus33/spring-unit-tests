package com.example.springdemotest.exceptions;

/**
 * Исключение при наличии пользователя.
 */
public class UserIsExistsException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public UserIsExistsException(String message) {
        super(message);
    }
}