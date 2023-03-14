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
import java.util.Optional;
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
        Tag firstTag = new Tag(1L, "first", Set.of(firstBook));
        Tag secondTag = new Tag(2L, "second", Set.of(secondBook));
        Set<Tag> tagsSet = Set.of(firstTag, secondTag);
        Tag[] tags = new Tag[]{firstTag, secondTag};
        when(tagRepository.findById(firstTag.getId())).thenReturn(Optional.of(firstTag));
        when(tagRepository.findById(secondTag.getId())).thenReturn(Optional.of(secondTag));
        Assert.assertEquals(tagsSet, tagService.mapTagArrayIntoTagSet(tags));
    }

    @Test
    void getAllTags() {
        Mockito.when(tagRepository.findAll()).thenReturn(List.of());
        Assert.assertEquals(tagService.getAllTags(), List.of());
        verify(tagRepository).findAll();
    }
}