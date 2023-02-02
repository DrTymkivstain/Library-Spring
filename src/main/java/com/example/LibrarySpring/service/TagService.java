package com.example.LibrarySpring.service;

import com.example.LibrarySpring.model.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Set<Tag> mapTagArrayIntoTagSet(Tag[] tags);

    List<String> getAllTags();
}
