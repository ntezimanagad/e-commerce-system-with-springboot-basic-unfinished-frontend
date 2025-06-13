package com.ecommerce.ecommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.UserDTO;
import com.ecommerce.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return ResponseEntity.ok("otp sent");
    }
    @PostMapping(value = "/validateRegister")
    public ResponseEntity<?> validate(@RequestBody UserDTO userDTO){
        userService.validateOtp(userDTO);
        return ResponseEntity.ok("validated");
    }
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNew(@RequestBody UserDTO userDTO){
        userService.createNew(userDTO);
        return ResponseEntity.ok("created");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO){
        userService.loginUser(userDTO);
        return ResponseEntity.ok("OTP sent");
    }
    @PostMapping(value = "/validateLogin")
    public ResponseEntity<?> validateLogin(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok( userService.validateLogin(userDTO));
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<?> resetPassword(@RequestBody UserDTO userDTO){
        userService.resetPassword(userDTO);
        return ResponseEntity.ok("OTP sent");
    }
    @PostMapping(value = "/validateReset")
    public ResponseEntity<?> validateReset(@RequestBody UserDTO userDTO){
        userService.validatePasswordReset(userDTO);
        return ResponseEntity.ok("validated");
    }
    @PostMapping(value = "/update")
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO userDTO){
        userService.updatePassword(userDTO);
        return ResponseEntity.ok("password updated");
    }

    public ResponseEntity<?> logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            userService.logout(token);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    

}
