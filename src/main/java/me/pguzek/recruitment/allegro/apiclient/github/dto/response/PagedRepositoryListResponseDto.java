package me.pguzek.recruitment.allegro.apiclient.github.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.RepositoryConnection;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.RepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.NamedStargazerNode;

@Data
@NoArgsConstructor
public class PagedRepositoryListResponseDto {
    private RepositoryConnection<RepositoryEdge<NamedStargazerNode>> repositories;
}
