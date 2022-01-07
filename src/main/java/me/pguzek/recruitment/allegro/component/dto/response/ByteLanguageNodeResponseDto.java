package me.pguzek.recruitment.allegro.component.dto.response;

import lombok.Builder;
import lombok.Data;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositoryedge.impl.SizeableRepositoryEdge;
import me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial.repositorynode.impl.LanguageNode;
import org.springframework.lang.NonNull;

import java.util.Map;

@Builder
@Data
public class ByteLanguageNodeResponseDto {
    private final String languageName;
    private final long totalBytes;

    public static ByteLanguageNodeResponseDto from(@NonNull Map.Entry<String, Long> object) {
        return ByteLanguageNodeResponseDto.builder()
                .languageName(object.getKey())
                .totalBytes(object.getValue())
                .build();
    }

    public static ByteLanguageNodeResponseDto from(@NonNull SizeableRepositoryEdge<LanguageNode> object) {
        return ByteLanguageNodeResponseDto.builder()
                .totalBytes(object.getSize())
                .languageName(object.getNode().getName())
                .build();
    }

}
