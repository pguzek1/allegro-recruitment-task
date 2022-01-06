package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.IRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@Data
@NoArgsConstructor
public class IdentifiableRepositoryEdge<T extends IRepositoryNode> implements IRepositoryEdge<T> {
    private String id;
    private String cursor;
    private T node;
}
