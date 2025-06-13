package com.ecommerce.ecommerce.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.UserDTO;
import com.ecommerce.ecommerce.jwt.JwtUtil;
import com.ecommerce.ecommerce.mapper.UserMapper;
import com.ecommerce.ecommerce.model.BlackList;
import com.ecommerce.ecommerce.model.Role;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.TokenBlacklistRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with This email exist");
        }else{
            otpService.generateOtp(userDTO.getEmail(), "REGISTER");
        }
    }

    public void validateOtp(UserDTO userDTO){
        boolean valid = otpService.validateOtp(userDTO.getEmail(), userDTO.getOtpCode(), "REGISTER");
        if (!valid) {
            throw new RuntimeException("OTP expired");        
        }
        otpService.deleteOtp(userDTO.getEmail(), "REGISTER");
    }

    public UserDTO createNew(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with This email exist");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.ADMIN);
        User user2 = userRepository.save(user);
        return UserMapper.toDto(user2);
    }

    public void loginUser(UserDTO userDTO){
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Passsword Combination Failed");
        }
        otpService.generateOtp(userDTO.getEmail(), "LOGIN");
    }
    public String validateLogin(UserDTO userDTO){
        boolean valid = otpService.validateOtp(userDTO.getEmail(), userDTO.getOtpCode(), "LOGIN");
        if (!valid) {
            throw new RuntimeException("Otp Expired");
        }
        otpService.deleteOtp(userDTO.getEmail(), "REGISTER");
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not found");
        }
        User user = optionalUser.get();
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public void resetPassword(UserDTO userDTO){
        Optional<User> s = userRepository.findByEmail(userDTO.getEmail());
        if (s.isPresent()) {
            otpService.generateOtp(userDTO.getEmail(), "RESET");
        }else{
            throw new RuntimeException("User Not found");
        }
    }
    public void validatePasswordReset(UserDTO userDTO){
        boolean valid = otpService.validateOtp(userDTO.getEmail(), userDTO.getOtpCode(), "RESET");
        if (!valid) {
            throw new RuntimeException("Failled to validate");
        }
        otpService.deleteOtp(userDTO.getEmail(), "RESET");
         
    }
    public UserDTO updatePassword(UserDTO userDTO){
        Optional<User> s = userRepository.findByEmail(userDTO.getEmail());
        if (s.isPresent()) {
            User ss = s.get();
            ss.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = userRepository.save(ss);
            return UserMapper.toDto(user);
        }
        throw new RuntimeException("User Not found");
    }

    public void logout(String token){
        BlackList blackList = new BlackList(token, Instant.now());
        tokenBlacklistRepository.save(blackList);
    }
}
