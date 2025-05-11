

package com.example.bookstore.Repository;

import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookTitle(String bookTitle);
    void deleteAllByBook(Book book);

}
