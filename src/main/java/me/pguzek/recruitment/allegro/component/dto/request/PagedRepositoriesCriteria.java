package me.pguzek.recruitment.allegro.component.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.request.impl.PagedRepositoriesGitHubRequestDto;
import me.pguzek.recruitment.allegro.component.dto.partial.OwnerAffiliation;
import me.pguzek.recruitment.allegro.component.dto.partial.PagedRepositoriesSort;
import me.pguzek.recruitment.allegro.component.dto.partial.RepositoryPrivacy;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PagedRepositoriesCriteria extends AbstractRequestCriteria {

    @Max(100)
    @Min(1)
    private long perPage = 30;

    @Nullable
    private String after;

    @NonNull
    private PagedRepositoriesSort.Direction direction = PagedRepositoriesSort.Direction.ASC;

    @NonNull
    private PagedRepositoriesSort.OrderBy orderBy = PagedRepositoriesSort.OrderBy.CREATED_AT;

    public PagedRepositoriesGitHubRequestDto toGitHubRequestDto() {
        return PagedRepositoriesGitHubRequestDto.builder()
                .login(getUser())
                .privacy(RepositoryPrivacy.to(getPrivacy()))
                .ownerAffiliations(OwnerAffiliation.to(getOwnerAffiliations()))
                .first(getPerPage())
                .after(getAfter())
                .orderBy(PagedRepositoriesSort.to(direction, orderBy))
                .build();
    }
}
