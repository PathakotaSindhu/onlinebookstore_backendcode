package com.example.bookstore.Controller;
import com.example.bookstore.Model.CartItem;
import com.example.bookstore.Model.User;
import com.example.bookstore.Repository.UserRepository;
import com.example.bookstore.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
private UserRepository userRepository;


//     @PostMapping("/add-by-name")
// public ResponseEntity<String> addToCartByBookName(
//         @RequestParam String bookName,
//         @RequestParam int quantity,
//         @AuthenticationPrincipal User user
// ) {
//     return ResponseEntity.ok(cartService.addToCartByBookName(bookName, quantity, user));
// }

@PostMapping("/add-by-name")
public ResponseEntity<String> addToCartByBookName(
        @RequestParam String bookName,
        @RequestParam int quantity) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();  // This will give you the email from the authenticated user
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return ResponseEntity.ok(cartService.addToCartByBookName(bookName, quantity, user));
}




@GetMapping("/view")
public ResponseEntity<List<CartItem>> getMyCartItems() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    List<CartItem> cartItems = cartService.getCartItemsForUser(user);
    return ResponseEntity.ok(cartItems);
}

@DeleteMapping("/deletebyname")
public ResponseEntity<String> deleteCartItemByBookName(@RequestParam String bookName) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    String response = cartService.deleteCartItemByBookName(bookName, user);
    return ResponseEntity.ok(response);
}

}
