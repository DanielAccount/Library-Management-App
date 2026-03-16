package com.practice.app.controller;

import com.practice.app.dto.BookDTO;
import com.practice.app.entity.Books;
import com.practice.app.serviceInterface.BooksInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksInterface booksInterface;

    //Without @RequestBody, Spring will not map the incoming JSON to your BookDTO, and all your fields will be null
    @PostMapping("/create")
    public Books saveBooks (@RequestBody BookDTO bookDTO){
        return booksInterface.createBooks(bookDTO);
    }

    @PutMapping("/update/{id}")
    public BookDTO updateBook (@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return booksInterface.updateBook(id, bookDTO);
    }

    @GetMapping("/all")
    public Page<Books> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size){
        return booksInterface.getAll(page, size);
    }

    @PostMapping("/search")
    public Page<Books> searchBooks(@RequestBody BookDTO bookDTO){
        return booksInterface.search(bookDTO);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Books> getBooksByIsbn(@PathVariable String isbn){
        Books books = booksInterface.findByIsbn(isbn);
        if(books != null){
            return ResponseEntity.ok(books);
        }else {
           return ResponseEntity.notFound().build();
        }
    }


}
