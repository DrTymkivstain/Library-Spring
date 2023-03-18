package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.BookAvailabilityStatus;
import com.example.LibrarySpring.model.Shelf;
import org.junit.jupiter.api.Test;

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
        Book book = Book.builder()
                .id(1L)
                .status(BookAvailabilityStatus.AVAILABLE)
                .name("book")
                .shelf(new Shelf())
                .build();
        BookDTO bookDTO = BookMapper.BOOK_MAPPER.toBookDTO(book);
        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());

    }
}