package com.practice.app.library.demo.acceptance;

import com.practice.app.dto.BookDTO;
import com.practice.app.entity.Books;
import com.practice.app.enums.Genre; // Import your Enum
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    // This MUST be the variable used in all steps
    private BookDTO requestBook;
    private ResponseEntity<Books> response;

    @Given("Book {string} by {string} with ISBN number {string}")
    public void prepareBook(String title, String author, String isbn) {
        // Initialize the DTO that the 'When' step will send
        requestBook = new BookDTO();
        requestBook.setTitle(title);
        requestBook.setAuthor(author);
        requestBook.setIsbn(isbn);

        // Map fields that match your DTO structure
        requestBook.setGenre(Genre.FICTION); // Use the Enum constant, not a String
        requestBook.setPageCount(500);
        requestBook.setIsAvailable(true);
        requestBook.setPublishedAt(LocalDateTime.now());
    }

    @When("I store the book in library")
    public void storeBook() {
        // Now requestBook is not null because we initialized it in 'Given'
        response = restTemplate.postForEntity("/api/books/create", requestBook, Books.class);
    }

    @Then("I am able to retrieve the book by the ISBN number")
    public void retrieveBook() {
        // Use the ISBN from the requestBook we prepared earlier
        ResponseEntity<Books> getResponse = restTemplate.getForEntity(
                "/api/books/" + requestBook.getIsbn(), Books.class);

        assertEquals(200, getResponse.getStatusCode().value());
        assertNotNull(getResponse.getBody());
        assertEquals(requestBook.getIsbn(), getResponse.getBody().getIsbn());
    }
}