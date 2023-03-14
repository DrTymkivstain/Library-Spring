package com.example.LibrarySpring.service.impl;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import com.example.LibrarySpring.model.*;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.ShelfRepository;
import com.example.LibrarySpring.repository.TagRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private AuthorServiceImpl authorService;
    @Mock
    private ShelfRepository shelfRepository;
    private Book book;
    private BookDTO bookDTO;
    private Shelf shelf;
    private Author author;
    private Tag tag;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void configTestData() {
        author = new Author(1L, "author", new HashSet<>());
        tag = new Tag(1L,"tag", new HashSet<>());
        shelf = new Shelf();
        book = Book.builder()
                .status(BookAvailabilityStatus.AVAILABLE)
                .id(1L)
                .name("test")
                .authors(Set.of(author))
                .tags(Set.of(tag))
                .shelf(shelf)
                .build();

        bookDTO = BookDTO.builder()
                .id(1L)
                .name("test")
                .authors(new Author[]{author})
                .tags(new Tag[]{tag})
                .build();
        book.setName("test");
    }


    @Test
    void addNewBook() {
        when(shelfRepository.findByBookIsNull()).thenReturn(Optional.of(shelf));
        when(tagService.mapTagArrayIntoTagSet(bookDTO.getTags())).thenReturn(book.getTags());
        when(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors())).thenReturn(book.getAuthors());
        when(bookRepository.save(book)).thenReturn(book);
        bookService.addNewBook(bookDTO);
        verify(shelfRepository).save(shelf);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook() {
        when(tagService.mapTagArrayIntoTagSet(bookDTO.getTags())).thenReturn(book.getTags());
        when(authorService.mapAuthorArrayIntoAuthorSet(bookDTO.getAuthors())).thenReturn(book.getAuthors());
        when(bookRepository.findById(bookDTO.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        bookService.updateBook(bookDTO);
        verify(bookRepository).findById(book.getId());
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook() {
        bookService.deleteBookById(bookDTO.getId());
        verify(bookRepository, times(1))
                .deleteById(bookDTO.getId());
    }

    @Test
    void getAvailableBooks() {
        Pageable pageable = PageRequest.of(1, 100);
        Mockito.when(bookRepository.findAllByStatus(BookAvailabilityStatus.AVAILABLE, pageable))
                .thenReturn(List.of());
        Assert.assertTrue(bookService.getAvailableBooks(pageable).isEmpty());
        when(bookRepository.findAllByStatus(BookAvailabilityStatus.AVAILABLE, pageable))
                .thenReturn(List.of(book));
        assertEquals(List.of(bookDTO), bookService.getAvailableBooks(pageable));
        verify(bookRepository, times(2)).findAllByStatus(BookAvailabilityStatus.AVAILABLE, pageable);
    }

    @Test
    void getAvailableBookDTOsByFilter() {
        Pageable pageable = PageRequest.of(1, 100);
        FilterDTO filterDTO = FilterDTO.builder()
                .authors(new String[]{"author"})
                .tags(new String[]{"tag"})
                .name("filter")
                .build();
        author.setAuthorName("author");
        tag.setName("tag");
        when(bookRepository.getAvailableBooksByFilter(filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(),
                pageable)).thenReturn(List.of(book));
        assertEquals(List.of(bookDTO), bookService.getAvailableBookDTOsByFilter(filterDTO, pageable));
        verify(bookRepository).getAvailableBooksByFilter(filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(),
                pageable);
    }
}