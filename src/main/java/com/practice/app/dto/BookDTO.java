package com.practice.app.dto;

import com.practice.app.enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private Integer pageCount;
    private Genre genre;
    private LocalDateTime publishedAt;
    private Boolean isAvailable;

    //filter
    private LocalDateTime startDate;
    private  LocalDateTime endDate;

    private int page;
    private int size;
}
