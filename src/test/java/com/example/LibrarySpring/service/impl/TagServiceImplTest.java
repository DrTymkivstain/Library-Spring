package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.Tag;
import com.example.LibrarySpring.repository.TagRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    TagRepository tagRepository;

    @Test
    void mapTagArrayIntoTagSet() {
        Book firstBook = new Book();
        Book secondBook = new Book();
        Set<Tag> tagsSet = Set.of(new Tag(1L, "first", Set.of(firstBook))
                , new Tag(1L, "second", Set.of(secondBook)));
        Tag[] tags = new Tag[]{new Tag(1L, "first", Set.of(firstBook))
                , new Tag(1L, "second", Set.of(secondBook))};
        Assert.assertEquals(tagsSet, tagService.mapTagArrayIntoTagSet(tags));
    }

    @Test
    void getAllTags() {
        Mockito.when(tagRepository.findAll()).thenReturn(List.of());
        Assert.assertEquals(tagService.getAllTags(), List.of());
        verify(tagRepository).findAll();
    }
}