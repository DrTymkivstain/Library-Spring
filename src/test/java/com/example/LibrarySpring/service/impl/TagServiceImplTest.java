package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.Tag;
import com.example.LibrarySpring.service.TagService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
@RunWith(SpringRunner.class)
@SpringBootTest
class TagServiceImplTest {
    @Autowired
    private TagService tagService;


    @Test
    void mapTagArrayIntoTagSet() {
        Book firstBook = new Book();
        Book secondBook = new Book();
        Set<Tag> tagsSet = Set.of(new Tag(1L, "first", Set.of(firstBook))
                ,new Tag(1L, "second", Set.of(secondBook)));
        Tag[] tags = new Tag[] {new Tag(1L, "first", Set.of(firstBook))
                ,new Tag(1L, "second", Set.of(secondBook))};
        Assert.assertEquals(tags, tagService.mapTagArrayIntoTagSet(tags));
    }

    @Test
    void getAllTags() {
    }
}