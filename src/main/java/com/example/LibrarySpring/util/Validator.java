package com.example.LibrarySpring.util;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.dto.FilterDTO;
import com.example.LibrarySpring.exception.CustomException;

import java.util.regex.Pattern;

public class Validator {
    public static void checkNewBook(BookDTO bookDTO) {
        if (Pattern.matches(ValidationRegex.bookName, bookDTO.getName()) &&
                bookDTO.getAuthors().length > 0 &&
                bookDTO.getTags().length > 0) {
            return;
        }
        throw new CustomException("impossible book values");
    }

    public static void checkFilterDTO(FilterDTO filterDTO) {
        if (filterDTO.getName().length() <= 2 ||
                filterDTO.getAuthors().length == 0 ||
                filterDTO.getTags().length == 0) {
            throw new CustomException("impossible.filter.values");
        }
    }
}