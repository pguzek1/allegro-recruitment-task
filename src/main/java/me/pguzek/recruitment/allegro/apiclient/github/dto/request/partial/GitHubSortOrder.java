package me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
