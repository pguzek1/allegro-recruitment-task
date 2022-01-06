package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.RepositoryConnection;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@Data
@NoArgsConstructor
public class LanguagesNode implements IRepositoryNode {
    private String id;
    private RepositoryConnection<SizeableRepositoryEdge<LanguageNode>> languages;
}
