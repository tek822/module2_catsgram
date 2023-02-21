package ru.yandex.practicum.catsgram.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super();
    }
    public PostNotFoundException(String message) {
        super(message);
    }
}
