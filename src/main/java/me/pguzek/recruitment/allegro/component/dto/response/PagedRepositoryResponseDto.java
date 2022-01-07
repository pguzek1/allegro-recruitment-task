package me.pguzek.recruitment.allegro.component.dto.response;

import lombok.Builder;
import lombok.Data;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.PagedRepositoryListResponseDto;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.RepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.NamedStargazerNode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class PagedRepositoryResponseDto {

    @NonNull
    private PageInfo pageInfo;

    @NonNull
    private List<RepositoryNode> repositories;


    @Data
    @Builder
    public static class RepositoryNode {
        @NonNull
        private String cursor;
        @NonNull
        private String name;
        private long stargazers;

        @NonNull
        public static RepositoryNode from(@NonNull RepositoryEdge<NamedStargazerNode> edge) {
            return RepositoryNode.builder()
                    .cursor(edge.getCursor())
                    .name(edge.getNode().getName())
                    .stargazers(edge.getNode().getStargazerCount())
                    .build();
        }

        @NonNull
        public static List<RepositoryNode> from(@NonNull List<RepositoryEdge<NamedStargazerNode>> edges) {
            return edges.stream().map(RepositoryNode::from).collect(Collectors.toList());
        }
    }

    @Data
    @Builder
    public static class PageInfo {
        @Nullable
        private String startCursor;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        @Nullable
        private String endCursor;

        @NonNull
        public static PageInfo from(@NonNull me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.PageInfo pageInfo) {
            return PageInfo.builder()
                    .startCursor(pageInfo.getStartCursor())
                    .hasPreviousPage(pageInfo.isHasPreviousPage())
                    .hasNextPage(pageInfo.isHasNextPage())
                    .endCursor(pageInfo.getEndCursor())
                    .build();

        }
    }

    @NonNull
    public static PagedRepositoryResponseDto from(@NonNull PagedRepositoryListResponseDto responseDto) {
        return PagedRepositoryResponseDto.builder()
                .pageInfo(PageInfo.from(responseDto.getRepositories().getPageInfo()))
                .repositories(RepositoryNode.from(responseDto.getRepositories().getEdges()))
                .build();
    }
}
