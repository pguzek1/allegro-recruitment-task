package me.pguzek.recruitment.allegro.apiclient.github.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubOwnerAffiliation;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubRepositoryPrivacy;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractGitHubRequestDto {

    @NotBlank
    private String login;

    @Nullable
    private String after;

    @Nullable
    private GitHubRepositoryPrivacy privacy;

    @NotEmpty
    private List<GitHubOwnerAffiliation> ownerAffiliations;

    private boolean organizationTypeUser;

    @SneakyThrows(JsonProcessingException.class)
    public AbstractGitHubRequestDto mutateAfterCursor(String endCursor) {
        final var objectMapper = new ObjectMapper();
        final var that = objectMapper.readValue(objectMapper.writeValueAsString(this), this.getClass());
        that.setAfter(endCursor);
        return that;
    }
}
