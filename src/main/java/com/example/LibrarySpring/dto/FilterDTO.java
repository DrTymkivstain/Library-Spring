package com.example.LibrarySpring.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    private String name;
    private String[] tags;
    private String[] authors;
}