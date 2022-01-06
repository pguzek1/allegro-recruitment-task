package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@Data
@NoArgsConstructor
public class NamedStargazerNode implements IRepositoryNode {
    private String name;
    private Long stargazerCount;
}
