package me.pguzek.recruitment.allegro.component.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TotalStargazerResponseDto {
    private long totalStargazer;

    public static TotalStargazerResponseDto from(Long object) {
        return TotalStargazerResponseDto.builder()
                .totalStargazer(object)
                .build();
    }
}
