package com.example.bookstore.Service;



import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Role;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.CartItemRepository;
import com.example.bookstore.Repository.ReviewRepository;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Security.JwtUtil;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;
    //to add books
       @Autowired
    private CartItemRepository cartItemRepository;

    public String addBook(Book book, String token) {
        String email = jwtUtil.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admin can add books");
        }

        bookRepository.save(book);
        return "Book added successfully!";
    }
    // to view books
   

public List<Book> getAllBooks(String token) {
    String email = jwtUtil.extractEmail(token);
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
            System.out.println("User Role: " + user.getRole());

    
    // ✅ You could add further access checks here if needed
    return bookRepository.findAll();
}

// public String deleteBookByTitle(String title) {
//         if (!bookRepository.findByTitle(title).isPresent()) {
//             throw new RuntimeException("❌ Book not found: " + title);
//         }
//         bookRepository.deleteByTitle(title);
//         return "✅ Book deleted successfully.";
//     }
@Transactional

public String deleteBookByTitle(String title) {
    List<Book> books = bookRepository.findAllByTitle(title);
    if (books.isEmpty()) throw new RuntimeException("Book not found");

    for (Book book : books) {
        reviewRepository.deleteAllByBook(book);
         cartItemRepository.deleteAllByBook(book);
        bookRepository.delete(book);
    }

    return "✅ All books with title '" + title + "' deleted successfully.";
}


}
 
