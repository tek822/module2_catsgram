package ru.yandex.practicum.catsgram.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super();
    }
    public InvalidEmailException(String message) {
        super(message);
    }
}
