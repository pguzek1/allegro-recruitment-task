package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@Data
@NoArgsConstructor
public class StargazerNode implements IRepositoryNode {
    private Long stargazerCount;
}
