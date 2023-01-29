package com.example.LibrarySpring.dto;

import com.example.LibrarySpring.entity.Author;
import com.example.LibrarySpring.entity.Tag;
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
