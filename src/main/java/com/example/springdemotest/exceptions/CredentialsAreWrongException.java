package com.example.springdemotest.exceptions;

/**
 * Исключение при неверных данных регистрации.
 */
public class CredentialsAreWrongException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public CredentialsAreWrongException(String message) {
        super(message);
    }
}