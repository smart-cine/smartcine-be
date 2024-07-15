package org.example.cinemamanagement.service;

import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.pagination.PageSpecificationTag;
import org.example.cinemamanagement.payload.request.AddTagRequest;
import org.example.cinemamanagement.payload.response.PageResponse;

import java.util.List;

public interface TagService {

    public PageResponse<List<String>> getAllTags(CursorBasedPageable cursorBasedPageable, PageSpecificationTag<Tag> pageSpecificationTag);

    public Boolean createTag(AddTagRequest addTagRequest);
}
