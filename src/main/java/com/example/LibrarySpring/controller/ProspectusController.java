package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import com.example.LibrarySpring.service.AuthorService;
import com.example.LibrarySpring.service.BookService;
import com.example.LibrarySpring.service.TagService;
import com.example.LibrarySpring.util.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class ProspectusController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final TagService tagService;

    public ProspectusController(BookService bookService, AuthorService authorService, TagService tagService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.tagService = tagService;
    }

    @GetMapping(value = "/books/{page}/{number}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<BookDTO> getAvailableBooks(@PathVariable("page") String page, @PathVariable("number") String number) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(number));
        log.info("get books by pageable {}", pageable);
        return bookService.getAvailableBooks(pageable);
    }

    @GetMapping(value = "/tags")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<String> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(value = "/authors")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<String> getAllAuthors() {
        return authorService.getAllAuthors();
    }


    @PostMapping("/filter/{page}/{number}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<BookDTO> getBooksByFilter(@PathVariable("page") String page,
                                   @PathVariable("number") String number,
                                   @RequestBody FilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(number));
        log.info("get books by pageable {}", pageable);
        log.info("get books by filter {}", filterDTO);
        Validator.checkFilterDTO(filterDTO);
        return bookService
                .getAvailableBookDTOsByFilter(filterDTO, pageable);
    }
}
