package com.ecommerce.ecommerce.mapper;

import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDto(OrderItem orderItem){
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtPurchase(orderItem.getPriceAtPurchase());
        dto.setOrder_id(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null);
        dto.setProduct_id(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null);
        return dto;
    }

    public static OrderItem toEntity(OrderItemDTO orderItemDTO){
        OrderItem dto = new OrderItem();
        dto.setId(orderItemDTO.getId());
        dto.setQuantity(orderItemDTO.getQuantity());
        return dto;
    }
}
