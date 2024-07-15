package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.mapper.TagMapper;
import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.pagination.PageSpecificationTag;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.repository.TagRepository;
import org.example.cinemamanagement.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public PageResponse<List<String>> getAllTags(CursorBasedPageable cursorBasedPageable, PageSpecificationTag<Tag> pageSpecificationTag) {
        try {
            var tagSlide = tagRepository.findAll(pageSpecificationTag,
                    Pageable.ofSize(cursorBasedPageable.getSize()));

            if (!tagSlide.hasContent()) return new PageResponse<>(false, null, null);
            Map<String, Object> pagingMap = new HashMap<>();

            List<Tag> tags = tagSlide.getContent();
            pagingMap.put("previousPageCursor", cursorBasedPageable.getEncodedCursor(tags.get(0).getName(), tagSlide.hasPrevious()));
            pagingMap.put("nextPageCursor", cursorBasedPageable.getEncodedCursor(tags.get(tags.size() - 1).getName(), tagSlide.hasNext()));
            pagingMap.put("size", cursorBasedPageable.getSize());
            pagingMap.put("total", tagSlide.getTotalElements());

            return new PageResponse<>(true, TagMapper.toTagList(tags), pagingMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean createTag(String tags) {
        return null;
    }
}
