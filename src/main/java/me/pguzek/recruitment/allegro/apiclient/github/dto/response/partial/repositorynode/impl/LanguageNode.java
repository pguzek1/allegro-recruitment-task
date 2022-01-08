package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.IRepositoryNode;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LanguageNode implements IRepositoryNode {
    private String name;
}
