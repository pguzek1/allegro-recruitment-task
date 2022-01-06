package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.IRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

import java.util.List;

@Data
@NoArgsConstructor
public class RepositoryConnection<T extends IRepositoryEdge<? extends IRepositoryNode>> {
    private List<T> edges;
    private PageInfo pageInfo;
}
