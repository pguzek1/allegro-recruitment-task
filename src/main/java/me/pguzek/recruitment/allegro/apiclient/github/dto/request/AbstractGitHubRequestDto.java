package me.pguzek.recruitment.allegro.apiclient.github.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoriesGitHubRequestDto;
import me.pguzek.recruitment.allegro.common.Patterns;
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

    @Nullable
    private PagedRepositoriesGitHubRequestDto.Privacy privacy;

    @NotEmpty
    private List<OwnerAffiliation> ownerAffiliations;

    private boolean organizationTypeUser;

    public enum Privacy {
        PUBLIC, PRIVATE
    }

    public enum OwnerAffiliation {
        OWNER, COLLABORATOR, ORGANIZATION_MEMBER
    }

    @SneakyThrows(JsonProcessingException.class)
    public AbstractGitHubRequestDto mutateAfterCursor(String endCursor) {
        final var objectMapper = new ObjectMapper();
        final var that = objectMapper.readValue(objectMapper.writeValueAsString(this), this.getClass());
        that.setAfter(endCursor);
        return that;
    }
}
