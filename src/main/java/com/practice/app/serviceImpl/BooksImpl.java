package com.practice.app.serviceImpl;

import com.practice.app.dto.BookDTO;
import com.practice.app.entity.Books;
import com.practice.app.repository.BookRepo;
import com.practice.app.serviceInterface.BooksInterface;
import com.practice.app.specification.BookSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BooksImpl implements BooksInterface {

    private final BookRepo repo;

    public BooksImpl (BookRepo repo){
        this.repo = repo;
    }

    @Override
    @Transactional
    public Books createBooks(BookDTO book) {

        log.info("Creating a new book with title {}", book.getTitle());

        Books entityBooks = Books.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .pageCount(book.getPageCount())
                .genre(book.getGenre())
                .isbn(book.getIsbn())
                .publishedAt(book.getPublishedAt())
                .isAvailable(book.getIsAvailable())
                .build();

        Books savedBook = repo.save(entityBooks);

        log.info("Book created with id: {}", savedBook.getId());
        return savedBook;
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Books existingBook = repo.findById(id)
                .orElseThrow(()-> new RuntimeException("Book with " + id + " not found"));
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPageCount(bookDTO.getPageCount());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublishedAt(bookDTO.getPublishedAt());
        existingBook.setIsAvailable(bookDTO.getIsAvailable());

        Books saveUpdate = repo.save(existingBook);
        log.info("Updated successfuly");

        return BookDTO.builder()
                .title(saveUpdate.getTitle())
                .author(saveUpdate.getAuthor())
                .pageCount(saveUpdate.getPageCount())
                .genre(saveUpdate.getGenre())
                .isbn(saveUpdate.getIsbn())
                .publishedAt(saveUpdate.getPublishedAt())
                .isAvailable(saveUpdate.getIsAvailable())
                .build();
    }

    @Override
    public Page<Books> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    public Page<Books> search(BookDTO bookDTO){
        Specification<Books> specification = BookSpecification.dynamicQuery(bookDTO);
        Pageable pageable = PageRequest.of(bookDTO.getPage(), bookDTO.getSize());

        return repo.findAll(specification,pageable);
    }
}
