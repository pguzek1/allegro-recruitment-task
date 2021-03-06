package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.IRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SizeableRepositoryEdge<T extends IRepositoryNode> implements IRepositoryEdge<T> {
    private long size;
    private String cursor;
    private T node;
}
