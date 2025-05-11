package com.example.bookstore.Repository;
import com.example.bookstore.Model.Book;
import java.util.List;
import java.util.Optional;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Optional<Book> findByTitle(String title);
    List<Book> findAllByTitle(String title);
    Optional<Book> findByTitle(String title);
     void deleteByTitle(String title);
    

}
