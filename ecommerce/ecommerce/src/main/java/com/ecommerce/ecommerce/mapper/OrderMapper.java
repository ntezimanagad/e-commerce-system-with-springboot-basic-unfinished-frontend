package com.ecommerce.ecommerce.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.ecommerce.dto.OrderDTO;
import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.Product;

public class OrderMapper {
    public static OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setPaymentInfo(order.getPaymentInfo());
        dto.setUser_id(order.getUser() != null ? order.getUser().getId() : null);

        List<OrderItemDTO> orderItemDTOs = order.getOrderItems() != null
            ? order.getOrderItems().stream()
                .map(OrderItemMapper::toDto)
                .collect(Collectors.toList())
            : new ArrayList<>();
        dto.setOrderItems(orderItemDTOs);
        return dto;
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        // DO NOT set ID when creating new orders — only when updating
        if (orderDTO.getId() != null) {
            order.setId(orderDTO.getId());
        }
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        order.setPaymentInfo(orderDTO.getPaymentInfo());

        // OrderItems won't be mapped here properly unless we also fetch Product and Order
        order.setOrderItems(new ArrayList<>()); // Leave empty for now

        return order;
    }

    // Optional: helper if you want to map OrderItemDTO to OrderItem fully
    public static OrderItem toMapOrderItem(OrderItemDTO orderItemDTO, Order order, Product product) {
        OrderItem orderItem = new OrderItem();
        if (orderItemDTO.getId() != null) {
            orderItem.setId(orderItemDTO.getId());
        }
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPriceAtPurchase(orderItemDTO.getPriceAtPurchase());
        return orderItem;
    }
}

