package com.example.bookstore.Controller;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Review;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.ReviewRepository;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Service.BookService;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;
     @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;
      @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book, @RequestHeader("Authorization") String authHeader) {
        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);
        return bookService.addBook(book, token);
    }
    @GetMapping("/allreviews")
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    @GetMapping("/reviewsByBookTitle")
    public List<Review> getReviewsByBookTitle(@RequestParam String bookTitle) {
        return reviewRepository.findByBookTitle(bookTitle);
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public List<Book> getAllBooks(@RequestHeader("Authorization") String authHeader) {
    String token = authHeader.substring(7); // remove "Bearer " prefix
    return bookService.getAllBooks(token);
}
@GetMapping("/allusers")
public List<User> getAllUsers() {
    return userRepository.findAll();
}
@GetMapping("/allbooks")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    @GetMapping("/searchByTitle")
    public Optional<Book> getBookByTitle(@RequestParam("title") String title) {
        return bookRepository.findByTitle(title);
    }
    @DeleteMapping("/delete")
    //@PreAuthorize("hasRole('ADMIN')") 
    public String deleteBookByTitle(@RequestParam String title) {
        return bookService.deleteBookByTitle(title);
    }
}

