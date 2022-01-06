package me.pguzek.recruitment.allegro.apiclient.github.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoryListRequestDto;
import me.pguzek.recruitment.allegro.common.Patterns;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractGitHubRequestDto {

    @NotBlank
    @Pattern(regexp = Patterns.GITHUB_USERNAME_PATTERN)
    private String login;

    @Nullable
    @Pattern(regexp = Patterns.BASE64_PATTERN)
    private String after;

    @NonNull
    private PagedRepositoryListRequestDto.Privacy privacy;

    @NotEmpty
    private List<OwnerAffiliation> ownerAffiliations;

    private boolean organizationTypeUser;

    public enum Privacy {
        PUBLIC, PRIVATE
    }

    public enum OwnerAffiliation {
        OWNER, COLLABORATOR, ORGANIZATION_MEMBER
    }
}
