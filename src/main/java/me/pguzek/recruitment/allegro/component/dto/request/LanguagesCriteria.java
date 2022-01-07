package me.pguzek.recruitment.allegro.component.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.LanguagesGitHubRequestDto;
import me.pguzek.recruitment.allegro.component.dto.partial.ByteLanguageNodeResponseComparatorFactory;
import me.pguzek.recruitment.allegro.component.dto.partial.OwnerAffiliation;
import me.pguzek.recruitment.allegro.component.dto.partial.RepositoryPrivacy;
import org.springframework.lang.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LanguagesCriteria extends AbstractRequestCriteria {

    @NonNull
    private ByteLanguageNodeResponseComparatorFactory.Direction direction = ByteLanguageNodeResponseComparatorFactory.Direction.DESC;

    @NonNull
    private ByteLanguageNodeResponseComparatorFactory.OrderBy orderBy = ByteLanguageNodeResponseComparatorFactory.OrderBy.BYTES;


    public LanguagesGitHubRequestDto toGitHubRequestDto() {
        return LanguagesGitHubRequestDto.builder()
                .login(getUser())
                .privacy(RepositoryPrivacy.to(getPrivacy()))
                .ownerAffiliations(OwnerAffiliation.to(this.getOwnerAffiliations()))
                .build();
    }
}
