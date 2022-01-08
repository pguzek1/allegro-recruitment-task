package me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class LanguagesGitHubRequestDto extends AbstractGitHubRequestDto {
    @Max(100)
    @Min(1)
    @Builder.Default
    private long first = 100;

    @Max(100)
    @Min(1)
    @Builder.Default
    private long firstLanguages = 100;

    @Nullable
    private String afterLanguages;

}
