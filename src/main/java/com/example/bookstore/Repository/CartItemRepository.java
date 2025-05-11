package com.example.bookstore.Repository;



import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.CartItem;
import com.example.bookstore.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndBookId(User user, Long bookId);
    void deleteByUserAndBook(User user, Book book);
    void deleteAllByBook(Book book);


}

