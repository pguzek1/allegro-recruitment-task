package me.pguzek.recruitment.allegro.exception;

public class AppException extends Exception {
    protected AppException(String message) {
        super(message);
    }

    protected AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
