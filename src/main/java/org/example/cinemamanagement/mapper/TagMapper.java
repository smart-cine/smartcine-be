package org.example.cinemamanagement.mapper;

import org.example.cinemamanagement.model.Tag;

import java.util.List;

public class TagMapper {
    static public List<String> toTagList(List<Tag> tags) {
        return tags.stream().map(Tag::getName).toList();
    }
}
