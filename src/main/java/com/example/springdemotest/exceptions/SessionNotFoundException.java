package com.example.springdemotest.exceptions;

/**
 * Исключение при отсутствии сессии.
 */
public class SessionNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public SessionNotFoundException(String message) {
        super(message);
    }
}