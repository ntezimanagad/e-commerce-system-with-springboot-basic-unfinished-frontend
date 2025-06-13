package com.ecommerce.ecommerce.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.ecommerce.dto.CartDTO;
import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;

public class CartMapper {

    public static CartDTO toDto(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUser_id(cart.getUser() != null ? cart.getUser().getId() : null);
        if (cart.getCartItems() != null) {
            List<CartItemDTO> items = cart.getCartItems()
                .stream()
                .map(CartItemMapper::toDto)
                .collect(Collectors.toList());
            dto.setCartItems(items);
        }
        return dto;
    }

    public static Cart toEntity(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        // Don't assign user or cartItems directly — let service do it
        return cart;
    }
}

