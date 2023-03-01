package ru.yandex.practicum.catsgram.exception;

public class IncorrectParameterException extends RuntimeException {
    final String parameter;

    public IncorrectParameterException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
