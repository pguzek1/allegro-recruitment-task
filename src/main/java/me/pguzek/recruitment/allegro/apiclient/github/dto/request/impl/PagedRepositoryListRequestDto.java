package me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class PagedRepositoryListRequestDto extends AbstractGitHubRequestDto {

    @NonNull
    @Max(100)
    @Min(1)
    @Builder.Default
    private long first = 30;

    @Nullable
    private SortOrder orderBy;

    @Builder
    @Data
    public static class SortOrder {

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

}
