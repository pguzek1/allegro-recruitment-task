package me.pguzek.recruitment.allegro.component.dto.partial;

import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubOwnerAffiliation;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public enum OwnerAffiliation {
    OWNER, COLLABORATOR, ORGANIZATION_MEMBER;

    public static List<GitHubOwnerAffiliation> to(@NonNull List<OwnerAffiliation> list) {
        return list.stream().map(x -> switch (x) {
            case OWNER -> GitHubOwnerAffiliation.OWNER;
            case COLLABORATOR -> GitHubOwnerAffiliation.COLLABORATOR;
            case ORGANIZATION_MEMBER -> GitHubOwnerAffiliation.ORGANIZATION_MEMBER;
        }).collect(Collectors.toList());
    }
}
