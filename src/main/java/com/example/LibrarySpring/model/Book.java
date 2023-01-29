package com.example.LibrarySpring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"shelf"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
    @OneToOne(optional = false, mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference
    private Shelf shelf;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "book_tag",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    private BookAvailabilityStatus status;

}
