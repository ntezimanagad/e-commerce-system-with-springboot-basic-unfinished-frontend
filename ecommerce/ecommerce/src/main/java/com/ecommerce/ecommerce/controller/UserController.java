package com.ecommerce.ecommerce.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @GetMapping(value = "/getuser")
    public String getUser() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String username = authentication.getName();
        return username;
    }

    @GetMapping(value = "/getuserid")
    public ResponseEntity<?> getUserId() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String email = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }

        User userId = optionalUser.get();

        return ResponseEntity.ok(Map.of("id", userId.getId()));

    }

    @GetMapping("/check-cart-owner/{cartId}")
    public ResponseEntity<?> checkCartOwnership(@PathVariable Long cartId) {
        // 1. Get authenticated username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 2. Get user from DB
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Get cart from DB
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // 4. Check ownership
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not own this cart");
        }

        // 5. Return success response (or cart DTO if needed)
        return ResponseEntity.ok("You own this cart.");
    }

}
