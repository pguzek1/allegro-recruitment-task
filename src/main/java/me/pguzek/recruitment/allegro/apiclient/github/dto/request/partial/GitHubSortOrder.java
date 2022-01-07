package me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Builder
public class GitHubSortOrder {

    @NonNull
    private Field field;

    @NonNull
    private Direction direction;


    public enum Field {
        CREATED_AT, UPDATED_AT, PUSHED_AT, NAME, STARGAZERS
    }

    public enum Direction {
        ASC, DESC
    }
}
