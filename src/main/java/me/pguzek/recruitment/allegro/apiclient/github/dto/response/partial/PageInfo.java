package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PageInfo {
    private String endCursor;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private String startCursor;
}
