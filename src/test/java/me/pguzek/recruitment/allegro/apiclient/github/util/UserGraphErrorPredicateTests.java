package me.pguzek.recruitment.allegro.apiclient.github.util;

import graphql.kickstart.spring.webclient.boot.GraphQLError;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserGraphErrorPredicateTests {

    @Test
    public void givenIncorrectThrowableType_thenExpectFalse() {
        // given
        Throwable t = new RuntimeException();

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.USER);

        //then
        assertFalse(x);
    }

    @Test
    public void givenCorrectThrowableType_thenExpectFalse() {
        // given
        Throwable t = new GraphQLErrorsException(List.of(new GraphQLError()));

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.USER);

        //then
        assertFalse(x);
    }

    @Test
    public void givenCorrectThrowableTypeWithInvalidUserType_thenExpectFalse() {
        // given
        final var error = new GraphQLError();
        error.setPath(List.of("organization"));
        Throwable t = new GraphQLErrorsException(List.of(error));

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.USER);

        //then
        assertFalse(x);
    }

    @Test
    public void givenCorrectThrowableTypeWithIncorrectSize_thenExpectFalse() {
        // given
        final var error1 = new GraphQLError();
        error1.setPath(List.of("organization"));
        final var error2 = new GraphQLError();
        error2.setPath(List.of("organization"));
        Throwable t = new GraphQLErrorsException(List.of(error1, error2));

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.ORGANIZATION);

        //then
        assertFalse(x);
    }

    @Test
    public void givenCorrectThrowableTypeWithUserType_whenPassUserType_thenExpectTrue() {
        // given
        final var error1 = new GraphQLError();
        error1.setPath(List.of("user"));
        Throwable t = new GraphQLErrorsException(List.of(error1));

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.USER);

        //then
        assertTrue(x);
    }

    @Test
    public void givenCorrectThrowableTypeWithOrganizationType_whenPassOrganizationType_thenExpectTrue() {
        // given
        final var error1 = new GraphQLError();
        error1.setPath(List.of("organization"));
        Throwable t = new GraphQLErrorsException(List.of(error1));

        // when
        final var x = UserGraphErrorPredicate.check(t, UserGraphErrorPredicate.UserType.ORGANIZATION);

        //then
        assertTrue(x);
    }

}
