package com.ecommerce.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.mapper.CartItemMapper;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.repository.CartItemRepository;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<CartItemDTO> getAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(CartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<CartItemDTO> getAllPage(Pageable pageable) {
        return cartItemRepository.findAll(pageable)
                .map(CartItemMapper::toDto);
    }

    public List<CartItemDTO> getByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream().map(CartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    public CartItemDTO createCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = CartItemMapper.toEntity(cartItemDTO);
        if (cartItemDTO.getCart_id() != null) {
            Cart cart = cartRepository.findById(cartItemDTO.getCart_id())
                    .orElseThrow(() -> new RuntimeException("This Id not found"));
            cartItem.setCart(cart);
        }
        if (cartItemDTO.getProduct_id() != null) {
            Product product = productRepository.findById(cartItemDTO.getProduct_id())
                    .orElseThrow(() -> new RuntimeException("This Id not found"));
            cartItem.setProduct(product);
        }
        CartItem saved = cartItemRepository.save(cartItem);
        return CartItemMapper.toDto(saved);
    }

    public void deleteCartItem(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
        } else {
            throw new RuntimeException("Id not found");
        }
    }
}
