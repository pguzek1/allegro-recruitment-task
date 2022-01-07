package me.pguzek.recruitment.allegro.apiclient.github.exception;

import me.pguzek.recruitment.allegro.exception.AppException;

public class UserNotFoundAppException extends AppException {

    private final static String USER_NOT_FOUND = "Provided user does not exist neither as user type nor organization type";

    protected UserNotFoundAppException(String message) {
        super(message);
    }

    protected UserNotFoundAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UserNotFoundAppException of(Throwable cause) {
        return new UserNotFoundAppException(USER_NOT_FOUND, cause);
    }
}
