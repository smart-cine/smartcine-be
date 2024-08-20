package org.example.cinemamanagement.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PagingModel {
    private String previousPageCursor;
    private String nextPageCursor;
    private int  limit;
    private long total;
}
