package me.pguzek.recruitment.allegro.component.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.AbstractGitHubRequestDto;
import me.pguzek.recruitment.allegro.common.Patterns;
import me.pguzek.recruitment.allegro.component.dto.partial.OwnerAffiliation;
import me.pguzek.recruitment.allegro.component.dto.partial.RepositoryPrivacy;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class AbstractRequestCriteria {

    @NotBlank
    @Pattern(regexp = Patterns.GITHUB_USERNAME_PATTERN)
    private String user;

    @NonNull
    private RepositoryPrivacy privacy = RepositoryPrivacy.PUBLIC;

    @NotEmpty
    private List<OwnerAffiliation> ownerAffiliations = List.of(OwnerAffiliation.OWNER);

    abstract public AbstractGitHubRequestDto toGitHubRequestDto();
}
