package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.CartDTO;
import com.ecommerce.ecommerce.service.CartService;

@RestController
@RequestMapping(value = "/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping(value = "/get")
    public ResponseEntity<List<CartDTO>> getAll(){
        return ResponseEntity.ok(cartService.getAll());
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<CartDTO>> getAllByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<CartDTO> page2 = cartService.getAllByPage(pageable);
        return ResponseEntity.ok(page2); 
    }
    @PostMapping(value = "/create")
    public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO){
        cartService.createCart(cartDTO);
        return ResponseEntity.ok("created");
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCart(@PathVariable Long id){
        cartService.deleteCart(id);
        return ResponseEntity.ok("Deleted");
    }

    
}
