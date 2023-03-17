package com.example.LibrarySpring.mapper;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.exception.CustomException;
import com.example.LibrarySpring.model.BookAvailabilityStatus;
import com.example.LibrarySpring.model.Order;
import com.example.LibrarySpring.repository.BookRepository;
import com.example.LibrarySpring.repository.UserRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface OrderMapper {

    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "bookName", target = "book.name")
    @Mapping(source = "userName", target = "user.username")
    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endDate", ignore = true)
    Order toOrder(OrderDTO orderDTO);
    @InheritInverseConfiguration
    @Mapping(source = "book.name", target = "bookName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
    OrderDTO toOrderDTO(Order order);
}
