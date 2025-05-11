// package com.example.bookstore.Controller;

// import com.example.bookstore.Model.Review;
// import com.example.bookstore.Service.ReviewService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/reviews")
// public class ReviewController {

//     @Autowired
//     private ReviewService reviewService;

//     @PostMapping("/add/{bookId}")
//     public ResponseEntity<String> addReview(@PathVariable Long bookId, @RequestBody Review review) {
//         reviewService.saveReview(review, bookId);
//         return ResponseEntity.ok("Review added successfully.");
//     }
// }

package com.example.bookstore.Controller;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Review;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.ReviewRepository;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Service.ReviewService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
 @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    // @PostMapping("/{bookId}")
   


// public ResponseEntity<String> addReview(
//         @PathVariable Long bookId,
//         @RequestBody Review review,
//         Principal principal) {

//     if (principal == null) {
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found.");
//     }

//     String email = principal.getName(); // This returns the email/username from the JWT token
//     User authenticatedUser = userRepository.findByEmail(email).orElse(null);

//     if (authenticatedUser == null) {
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in DB.");
//     }

//     Optional<Book> optionalBook = bookRepository.findById(bookId);
//     if (optionalBook.isEmpty()) {
//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
//     }

//     // Set relationships
//     review.setBook(optionalBook.get());
//     review.setUser(authenticatedUser);

//     // Set full name and email from User object
//     review.setFullName(authenticatedUser.getFullName());
//     review.setEmail(authenticatedUser.getEmail());

//     reviewRepository.save(review);
//     return ResponseEntity.ok("Review submitted successfully.");
// }

@PostMapping("/{bookTitle}")
public ResponseEntity<String> addReviewByBookTitle(
        @PathVariable String bookTitle,
        @RequestBody Review review,
        Principal principal) {

    if (principal == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found.");
    }

    String email = principal.getName();
    User authenticatedUser = userRepository.findByEmail(email).orElse(null);

    if (authenticatedUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in DB.");
    }

    // Optional<Book> optionalBook = bookRepository.findByTitle(bookTitle);
    // if (optionalBook.isEmpty()) {
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    // }

    // review.setBook(optionalBook.get());
    List<Book> books = bookRepository.findAllByTitle(bookTitle);
if (books.isEmpty()) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
}
if (books.size() > 1) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body("Multiple books found with the same title. Please use a unique identifier.");
}
Book book = books.get(0);
review.setBook(book);

    review.setUser(authenticatedUser);
    review.setFullName(authenticatedUser.getFullName());
    review.setEmail(authenticatedUser.getEmail());
    review.setBookTitle(book.getTitle());

    reviewRepository.save(review);
    return ResponseEntity.ok("Review submitted successfully for book: " + bookTitle);
}


}
