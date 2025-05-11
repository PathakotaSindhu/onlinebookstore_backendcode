package com.example.bookstore.Service;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Review;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.ReviewRepository;
import com.example.bookstore.Repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public String addReview(Long bookId, Review review, User authenticatedUser) {
        if (authenticatedUser == null) {
            return "Authenticated user not found.";
        }
    
        Optional<Book> optionalBook = bookRepository.findById(bookId);
    
        if (optionalBook.isEmpty()) {
            return "Book not found.";
        }
    
        // Set the book and authenticated user
        review.setBook(optionalBook.get());
        review.setUser(authenticatedUser);
    
        // Optionally, print or log details
        System.out.println("User ID: " + authenticatedUser.getId());
        System.out.println("Email: " + authenticatedUser.getEmail());
        System.out.println("Full Name: " + authenticatedUser.getFullName());
    
        reviewRepository.save(review);
        return "Review submitted successfully.";
    }
    
    
}
