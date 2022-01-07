package me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class StargazersGitHubRequestDto extends AbstractGitHubRequestDto {
    @NonNull
    @Max(100)
    @Min(1)
    @Builder.Default
    private long first = 100;

    //TODO: sort by stargazers, if last node on a page has zero stargazers then we dont need to get another page
}
