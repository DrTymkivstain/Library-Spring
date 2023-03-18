package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.model.*;
import org.junit.jupiter.api.Test;

import javax.swing.text.TabableView;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    void toBook() {
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .name("book")
                .build();
        Book book = BookMapper.BOOK_MAPPER.toBook(bookDTO);
        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());
    }

    @Test
    void toBookDTO() {
        Author author = new Author();
        author.setId(2L);
        Tag tag = new Tag();
        tag.setName("tag");
        Book book = Book.builder()
                .id(1L)
                .status(BookAvailabilityStatus.AVAILABLE)
                .authors(Set.of(author))
                .tags(Set.of(tag))
                .name("book")
                .shelf(new Shelf())
                .build();
        Tag[] tags = book.getTags().toArray(new Tag[book.getTags().size()]);
        Author[] authors = book.getAuthors().toArray(new Author[book.getAuthors().size()]);
        BookDTO bookDTO = BookMapper.BOOK_MAPPER.toBookDTO(book);
        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());
        assertArrayEquals(tags, bookDTO.getTags());
        assertArrayEquals(authors, bookDTO.getAuthors());
    }
}