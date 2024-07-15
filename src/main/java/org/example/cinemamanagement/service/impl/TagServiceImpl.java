package org.example.cinemamanagement.service.impl;

import org.example.cinemamanagement.mapper.TagMapper;
import org.example.cinemamanagement.model.Film;
import org.example.cinemamanagement.model.Tag;
import org.example.cinemamanagement.pagination.CursorBasedPageable;
import org.example.cinemamanagement.pagination.PageSpecificationTag;
import org.example.cinemamanagement.payload.request.AddOrDeleteTagRequest;
import org.example.cinemamanagement.payload.response.PageResponse;
import org.example.cinemamanagement.repository.FilmRepository;
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

    @Autowired
    FilmRepository filmRepository;

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
    public Boolean createTag(AddOrDeleteTagRequest addTagRequest) {
        try {
            if (addTagRequest.getFilmId() == null) {
                throw new RuntimeException("Film not found");
            }
            Film film = filmRepository.findById(addTagRequest.getFilmId()).orElseThrow(() -> {
                throw new RuntimeException("Film not found");
            });

            for (String tag : addTagRequest.getTags()) {
                film.getTags().add(tagRepository.findTagByName(tag).orElseGet(() -> {
                    return tagRepository.save(Tag.builder()
                            .name(tag)
                            .build());
                }));
            }

            filmRepository.save(film);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteTag(AddOrDeleteTagRequest deleteTagRequest) {
        Film film = filmRepository.findById(deleteTagRequest.getFilmId()).orElseThrow(() -> {
            throw new RuntimeException("Film not found");
        });

        List<Tag> tags = film.getTags();
        for (String tag : deleteTagRequest.getTags()) {
            try {
                Tag tagToDelete = tagRepository.findTagByName(tag).orElseThrow(() -> {
                    throw new RuntimeException("Tag not found");
                });
                tags.remove(tagToDelete);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        filmRepository.save(film);
        return true;
    }
}
