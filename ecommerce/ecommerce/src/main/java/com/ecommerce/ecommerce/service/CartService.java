package com.ecommerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.CartDTO;
import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.mapper.CartItemMapper;
import com.ecommerce.ecommerce.mapper.CartMapper;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<CartDTO> getAll() {
        return cartRepository.findAll()
                .stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<CartDTO> getAllByPage(Pageable pageable) {
        return cartRepository.findAll(pageable)
                .map(CartMapper::toDto);
    }

    public CartDTO createCart(CartDTO cartDTO) {
        Cart cart = CartMapper.toEntity(cartDTO);

        // Set User
        if (cartDTO.getUser_id() != null) {
            User user = userRepository.findById(cartDTO.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            cart.setUser(user);
        }

        // Save empty cart first to generate ID
        Cart savedCart = cartRepository.save(cart);

        // Set Cart Items if any
        if (cartDTO.getCartItems() != null) {
            List<CartItem> items = new ArrayList<>();
            for (CartItemDTO itemDTO : cartDTO.getCartItems()) {
                CartItem item = CartItemMapper.toEntity(itemDTO);

                if (itemDTO.getProduct_id() != null) {
                    Product product = productRepository.findById(itemDTO.getProduct_id())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    item.setProduct(product);
                }

                item.setCart(savedCart); // VERY IMPORTANT
                items.add(item);
            }

            savedCart.setCartItems(items);
            savedCart = cartRepository.save(savedCart); // save with items
        }

        return CartMapper.toDto(savedCart);
    }

    public void deleteCart(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cart Not dound");
        }
    }
}
