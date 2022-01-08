package me.pguzek.recruitment.allegro.component.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Builder
@Data
public class TotalStargazerResponseDto {
    private long totalStargazer;

    public static TotalStargazerResponseDto from(@NonNull Long totalStargazer) {
        return TotalStargazerResponseDto.builder()
                .totalStargazer(totalStargazer)
                .build();
    }
}
