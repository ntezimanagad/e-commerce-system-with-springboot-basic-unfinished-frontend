package com.ecommerce.ecommerce.mapper;

import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Product;

public class CartItemMapper {

    public static CartItemDTO toDto(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setCart_id(cartItem.getCart() != null ? cartItem.getCart().getId() : null);
        dto.setProduct_id(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null);
        return dto;
    }

    public static CartItem toEntity(CartItemDTO cartItemDTO){
    CartItem cartItem = new CartItem();
    cartItem.setId(cartItemDTO.getId());
    cartItem.setQuantity(cartItemDTO.getQuantity());

    if (cartItemDTO.getCart_id() != null) {
        Cart cart = new Cart();
        cart.setId(cartItemDTO.getCart_id());
        cartItem.setCart(cart);
    }

    if (cartItemDTO.getProduct_id() != null) {
        Product product = new Product();
        product.setId(cartItemDTO.getProduct_id());
        cartItem.setProduct(product);
    }

    return cartItem;
}

}
