package com.practice.app.entity;

import com.practice.app.enums.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Integer pageCount;
    @Enumerated(EnumType.STRING)// stores "FICTION", "SCIENCE", "HISTORY"
    private Genre genre;
    private LocalDateTime publishedAt;
    private Boolean isAvailable;


}
