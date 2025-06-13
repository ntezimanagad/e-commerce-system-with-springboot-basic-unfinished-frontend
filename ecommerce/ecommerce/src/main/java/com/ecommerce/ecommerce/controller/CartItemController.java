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

import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.service.CartItemService;

@RestController
@RequestMapping(value = "/api/cartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<CartItemDTO>> getAll() {
        return ResponseEntity.ok(cartItemService.getAll());
    }

    @GetMapping(value = "/by-cart/{cartId}")
    public ResponseEntity<List<CartItemDTO>> getByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getByCartId(cartId));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<CartItemDTO>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CartItemDTO> page2 = cartItemService.getAllPage(pageable);
        return ResponseEntity.ok(page2);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createItem(@RequestBody CartItemDTO cartItemDTO) {
        cartItemService.createCartItem(cartItemDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Deleted");
    }
}
