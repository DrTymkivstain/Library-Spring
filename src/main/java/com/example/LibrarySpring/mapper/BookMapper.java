package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.model.Tag;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BookMapper {

    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);
    @Mapping(target = "shelf", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Book toBook(BookDTO bookDTO);


    @InheritInverseConfiguration
    BookDTO toBookDTO(Book book);

    @AfterMapping
    default void SetAuthorsAndTags(@MappingTarget BookDTO bookDTO, Book book) {
        bookDTO.setTags(book.getTags().toArray(new Tag[book.getTags().size()]));
        bookDTO.setAuthors(book.getAuthors().toArray(new Author[book.getAuthors().size()]));
    }
}
