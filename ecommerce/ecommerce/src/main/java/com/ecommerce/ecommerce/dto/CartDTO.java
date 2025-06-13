package com.ecommerce.ecommerce.dto;

import java.util.List;

public class CartDTO {
    private Long id;
    private Long user_id;
    private List<CartItemDTO> cartItems;
    public CartDTO() {
    }

    public CartDTO(Long user_id, List<CartItemDTO> cartItems) {
        this.user_id = user_id;
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    
}
