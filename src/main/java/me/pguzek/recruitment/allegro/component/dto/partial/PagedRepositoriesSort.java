package me.pguzek.recruitment.allegro.component.dto.partial;

import lombok.Builder;
import lombok.Data;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubSortOrder;
import org.springframework.lang.NonNull;

@Builder
@Data
public class PagedRepositoriesSort {

    @NonNull
    private Direction direction;

    @NonNull
    private OrderBy orderBy;


    public enum Direction {
        ASC, DESC;

        @NonNull
        public static GitHubSortOrder.Direction to(@NonNull Direction direction) {
            return switch (direction) {
                case ASC -> GitHubSortOrder.Direction.ASC;
                case DESC -> GitHubSortOrder.Direction.DESC;
            };
        }
    }

    public enum OrderBy {
        CREATED_AT, UPDATED_AT, PUSHED_AT, NAME, STARGAZERS;

        @NonNull
        public static GitHubSortOrder.Field to(@NonNull OrderBy orderBy) {
            return switch (orderBy) {
                case NAME -> GitHubSortOrder.Field.NAME;
                case PUSHED_AT -> GitHubSortOrder.Field.PUSHED_AT;
                case CREATED_AT -> GitHubSortOrder.Field.CREATED_AT;
                case STARGAZERS -> GitHubSortOrder.Field.STARGAZERS;
                case UPDATED_AT -> GitHubSortOrder.Field.UPDATED_AT;
            };
        }
    }

    @NonNull
    public static GitHubSortOrder to(@NonNull Direction direction, @NonNull OrderBy orderBy) {
        return GitHubSortOrder.builder()
                .direction(Direction.to(direction))
                .field(OrderBy.to(orderBy))
                .build();
    }
}
