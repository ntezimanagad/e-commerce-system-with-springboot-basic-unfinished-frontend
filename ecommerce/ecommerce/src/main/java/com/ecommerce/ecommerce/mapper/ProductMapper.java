package com.ecommerce.ecommerce.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.dto.ProductDTO;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.Product;

public class ProductMapper {
    public static ProductDTO toDto(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescrption(product.getDescrption());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        
        List<CartItemDTO> cartItemDTOs = product.getCartItems() != null
            ? product.getCartItems().stream()
                .map(CartItemMapper::toDto)
                .collect(Collectors.toList())
            : new ArrayList<>();

        dto.setCartItems(cartItemDTOs);

        List<OrderItemDTO> orderItemDTOs = product.getOrderItems() != null
            ? product.getOrderItems().stream()
                .map(OrderItemMapper::toDto)
                .collect(Collectors.toList())
            : new ArrayList<>();

        dto.setOrderItems(orderItemDTOs);
        return dto;
    }

    public static Product toEntity(ProductDTO productDTO){
        Product dto = new Product();
        dto.setId(productDTO.getId());
        dto.setName(productDTO.getName());
        dto.setPrice(productDTO.getPrice());
        dto.setDescrption(productDTO.getDescrption());
        dto.setStock(productDTO.getStock());
        dto.setImageUrl(productDTO.getImageUrl());
        
        List<CartItemDTO> cartItemDTOs = productDTO.getCartItems() != null
            ? productDTO.getCartItems()
            : new ArrayList<>();
        
        List<CartItem> cartItems = cartItemDTOs.stream()
            .map(ProductMapper::toMapCartItem)
            .collect(Collectors.toList());

        dto.setCartItems(cartItems);

        List<OrderItemDTO> orderItemDTOs = productDTO.getOrderItems() != null
            ? productDTO.getOrderItems()
            : new ArrayList<>();
        
        List<OrderItem> orderItems = orderItemDTOs.stream()
            .map(ProductMapper::toMapOrderItem)
            .collect(Collectors.toList());

        dto.setOrderItems(orderItems);
        return dto;
    }

    public static OrderItem toMapOrderItem(OrderItemDTO orderItemDTO){
        OrderItem dto = new OrderItem();
        dto.setId(orderItemDTO.getId());
        dto.setQuantity(orderItemDTO.getQuantity());
        return dto;
    }

    public static CartItem toMapCartItem(CartItemDTO cartItemDTO){
        CartItem dto = new CartItem();
        dto.setId(cartItemDTO.getId());
        dto.setQuantity(cartItemDTO.getQuantity());
        return dto;
    }


}
