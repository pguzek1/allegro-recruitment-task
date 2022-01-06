package me.pguzek.recruitment.allegro.apiclient.github.dto.response.partial;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageInfo {
    public String endCursor;
    public boolean hasNextPage;
    public boolean hasPreviousPage;
    public String startCursor;
}
