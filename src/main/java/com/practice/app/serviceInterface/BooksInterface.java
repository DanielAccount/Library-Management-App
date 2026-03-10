package com.practice.app.serviceInterface;

import com.practice.app.dto.BookDTO;
import com.practice.app.entity.Books;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BooksInterface {
    Books createBooks(BookDTO book);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    Page<Books> getAll(int page, int size);
    Page<Books> search(BookDTO bookDTO);
}
