package com.example.bookstore.Service;
import com.example.bookstore.Model.Book;
import com.example.bookstore.Model.CartItem;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.BookRepository;
import com.example.bookstore.Repository.CartItemRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;
    public String addToCartByBookName(String bookName, int quantity, User user) {
        Book book = bookRepository.findByTitle(bookName)
                .orElseThrow(() -> new RuntimeException("Book not found with name: " + bookName));
    
        CartItem existingItem = cartItemRepository.findByUserAndBookId(user, book.getId())
                .orElse(null);
                System.out.println("User ID: " + user.getId()); // should not be null

    
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem(book, quantity, user);
            cartItemRepository.save(newItem);
        }
    
        return "Book added to cart";
    }
    
    
    // public List<CartItem> getCartItems(User user) {
    //     return cartItemRepository.findByUser(user);
    // }

    public List<CartItem> getCartItemsForUser(User user) {
        return cartItemRepository.findByUser(user);
    }
    
    @Transactional
    public String deleteCartItemByBookName(String bookName, User user) {
        Book book = bookRepository.findByTitle(bookName)
                .orElseThrow(() -> new RuntimeException("Book not found with name: " + bookName));
    
        CartItem existingItem = cartItemRepository.findByUserAndBookId(user, book.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found for the given book and user"));
    
        cartItemRepository.deleteByUserAndBook(user, book);
        return "Cart item deleted successfully";
    }
    
}
