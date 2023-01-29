package com.example.LibrarySpring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order")
public class Order {
    private static final Integer PERIOD_OF_USE_IN_MONTHS = 1;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = startDate.plusMonths(PERIOD_OF_USE_IN_MONTHS);

}
