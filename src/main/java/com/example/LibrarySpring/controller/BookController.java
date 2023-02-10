package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('developers:write')")
    public BookDTO addBook(@RequestBody BookDTO bookDTO) {
        log.info("add new book {}", bookDTO);
        bookService.addNewBook(bookDTO);
        return  bookDTO;
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('developers:write')")
    public BookDTO updateBook(@RequestBody BookDTO bookDTO) {
        log.info("update book {}", bookDTO);
        bookService.updateBook(bookDTO);
        return  bookDTO;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void deleteBook(@PathVariable Long id) {
        log.info("delete book with id {}", id);
        bookService.deleteBookById(id);
    }
}
