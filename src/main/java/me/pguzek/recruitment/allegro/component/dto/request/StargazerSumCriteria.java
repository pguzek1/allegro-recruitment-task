package me.pguzek.recruitment.allegro.component.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.StargazersGitHubRequestDto;
import me.pguzek.recruitment.allegro.component.dto.partial.OwnerAffiliation;
import me.pguzek.recruitment.allegro.component.dto.partial.RepositoryPrivacy;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class StargazerSumCriteria extends AbstractRequestCriteria {

    public StargazersGitHubRequestDto toGitHubRequestDto() {
        return StargazersGitHubRequestDto.builder()
                .login(getUser())
                .privacy(RepositoryPrivacy.to(getPrivacy()))
                .ownerAffiliations(OwnerAffiliation.to(this.getOwnerAffiliations()))
                .build();
    }
}
