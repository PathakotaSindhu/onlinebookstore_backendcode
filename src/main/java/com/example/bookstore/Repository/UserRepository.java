// package com.example.bookstore.Repository;



// import org.springframework.data.jpa.repository.JpaRepository;

// import com.example.bookstore.Model.Role;
// import com.example.bookstore.Model.User;

// import java.util.Optional;

// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByEmail(String email);
//     boolean existsByRole(Role role);  // Now use Role enum for checking
// }
package com.example.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.Model.Role;
import com.example.bookstore.Model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRole(Role role); // to ensure only one ADMIN
    Optional<User> findByResetToken(String resetToken);
}
