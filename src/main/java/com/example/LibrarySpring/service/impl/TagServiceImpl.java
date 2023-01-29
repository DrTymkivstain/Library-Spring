package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Tag;
import com.example.LibrarySpring.repository.TagRepository;
import com.example.LibrarySpring.service.TagService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> mapTagArrayIntoTagSet(Tag[] tags) {
        return Arrays.stream(tags)
                .collect(Collectors.toSet());
    }
}
