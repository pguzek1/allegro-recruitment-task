package me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubSortOrder;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class PagedRepositoriesGitHubRequestDto extends AbstractGitHubRequestDto {

    @Max(100)
    @Min(1)
    @Builder.Default
    private long first = 30;

    @NonNull
    private GitHubSortOrder orderBy = GitHubSortOrder.builder()
            .direction(GitHubSortOrder.Direction.ASC)
            .field(GitHubSortOrder.Field.CREATED_AT)
            .build();

}
