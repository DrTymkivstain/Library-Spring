package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.BookDTO;
import com.example.LibrarySpring.model.Book;
import com.example.LibrarySpring.service.TagService;
import com.example.LibrarySpring.service.impl.TagServiceImpl;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface BookMapper {


}
