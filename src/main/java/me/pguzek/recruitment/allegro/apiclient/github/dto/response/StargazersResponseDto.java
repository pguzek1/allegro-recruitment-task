package me.pguzek.recruitment.allegro.apiclient.github.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.RepositoryConnection;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.RepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.StargazerNode;

@Data
@NoArgsConstructor
public class StargazersResponseDto {
    private RepositoryConnection<RepositoryEdge<StargazerNode>> repositories;
}
