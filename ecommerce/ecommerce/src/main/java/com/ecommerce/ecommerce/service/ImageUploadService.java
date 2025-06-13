package com.ecommerce.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ImageUploadService {
    
    private final String uploadDir = "uploads/products/";
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
    
    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("File must be an image");
        }
        
        try {
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            
            // Save file
            Path filePath = Paths.get(uploadDir + uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return "/api/images/" + uniqueFilename; // Return URL path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
    
    public void deleteImage(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("/api/images/")) {
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            try {
                Files.deleteIfExists(Paths.get(uploadDir + filename));
            } catch (IOException e) {
                // Log error but don't throw exception
                System.err.println("Failed to delete image: " + e.getMessage());
            }
        }
    }
    
    public Resource loadImage(String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
}

