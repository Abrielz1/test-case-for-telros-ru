package ru.telros.test_case_for_telros_ru.exception.exceptions;

public class UserIsDeletedException extends RuntimeException {

    public UserIsDeletedException(String message) {
        super((message));
    }
}
