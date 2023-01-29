package com.example.LibrarySpring.dto;

import com.example.LibrarySpring.model.Author;
import com.example.LibrarySpring.model.Tag;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String name;
    private Author[] authors;
    private Tag[] tags;
}
