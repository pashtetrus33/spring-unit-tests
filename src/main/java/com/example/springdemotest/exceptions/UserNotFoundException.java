package com.example.springdemotest.exceptions;

/**
 * Исключение при отсутствии пользователя.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}