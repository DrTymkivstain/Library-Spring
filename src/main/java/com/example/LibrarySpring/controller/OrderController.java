package com.example.LibrarySpring.controller;

import com.example.LibrarySpring.dto.OrderDTO;
import com.example.LibrarySpring.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/permit")
    public OrderDTO createOrder(OrderDTO orderDTO) {
        log.info("create order {}", orderDTO);
        orderService.createOrder(orderDTO);
        return orderDTO;
    }

    @PutMapping("/return")
    public OrderDTO closeOrderAndReturnBook(OrderDTO orderDTO) {
        log.info("close order {}", orderDTO);
        return orderDTO;
    }

    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return null;
    }

    @GetMapping("/active")
    public List<OrderDTO> getAllActiveOrders() {
        return null;
    }

    @GetMapping("/passive")
    public List<OrderDTO> getAllPassiveOrders() {
        return null;
    }

    @GetMapping("/user/active")
    public List<OrderDTO> getActiveOrdersByUser() {
        return null;
    }

    @GetMapping("/user/passive")
    public List<OrderDTO> getPassiveOrdersByUser() {
        return null;
    }

    @GetMapping("user/all")
    public List<OrderDTO> getAllOrdersByUser() {
        return null;
    }
}
