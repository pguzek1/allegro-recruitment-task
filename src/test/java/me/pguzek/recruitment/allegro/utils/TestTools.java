package me.pguzek.recruitment.allegro.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestTools {

    /**
     * Create object using package-private constructor of GraphQLResponse
     *
     * @param resourcePath response content
     * @return instance of GraphQLResponse
     */
    public static GraphQLResponse buildResponse(String resourcePath) {
        try {
            final Constructor<GraphQLResponse> constructor = GraphQLResponse.class.getDeclaredConstructor(String.class, ObjectMapper.class);
            constructor.setAccessible(true);

            final var mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            return constructor.newInstance(readFile(resourcePath), mapper);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String resourcePath) {
        final var resource = new ClassPathResource(resourcePath);
        try (final var inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
