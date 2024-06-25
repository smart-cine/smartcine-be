package org.example.cinemamanagement.utils;

import lombok.Builder;
import okhttp3.internal.http2.Http2Reader;

public record PageResponse<T>(Boolean success, T data, Object paging) {
}
