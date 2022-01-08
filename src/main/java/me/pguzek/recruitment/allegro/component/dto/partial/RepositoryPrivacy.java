package me.pguzek.recruitment.allegro.component.dto.partial;

import me.pguzek.recruitment.allegro.apiclient.github.dto.request.partial.GitHubRepositoryPrivacy;
import org.springframework.lang.NonNull;

public enum RepositoryPrivacy {
    ALL, PUBLIC, PRIVATE;

    public static GitHubRepositoryPrivacy to(@NonNull RepositoryPrivacy privacy) {
        return switch (privacy) {
            case ALL -> null;
            case PUBLIC -> GitHubRepositoryPrivacy.PUBLIC;
            case PRIVATE -> GitHubRepositoryPrivacy.PRIVATE;
        };
    }
}
