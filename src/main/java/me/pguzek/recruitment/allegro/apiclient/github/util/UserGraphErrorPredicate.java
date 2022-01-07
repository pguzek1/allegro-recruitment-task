package me.pguzek.recruitment.allegro.apiclient.github.util;

import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.BiPredicate;

public class UserGraphErrorPredicate implements BiPredicate<Throwable, UserGraphErrorPredicate.UserType> {
    /**
     * Check if error is related with user existence.
     *
     * @param throwable occured exception
     * @param userType type of user account
     * @return if user of type exists
     */
    @Override
    public boolean test(@NonNull Throwable throwable, @NonNull UserType userType) {
        if (throwable instanceof GraphQLErrorsException) {
            final var errors = ((GraphQLErrorsException) throwable).getErrors();

            return errors.size() == 1
                    && Objects.nonNull(errors.get(0).getPath())
                    && errors.get(0).getPath().size() == 1
                    && userType.getValue().equals(errors.get(0).getPath().get(0));
        }
        return false;
    }


    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public enum UserType {
        USER("user"),
        ORGANIZATION("organization");
        private final String value;
    }

    public static boolean check(@NonNull Throwable throwable, @NonNull UserType userType) {
        return new UserGraphErrorPredicate().test(throwable, userType);
    }
}
