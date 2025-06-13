package com.ecommerce.ecommerce.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecommerce.ecommerce.dto.CartDTO;
import com.ecommerce.ecommerce.dto.CartItemDTO;
import com.ecommerce.ecommerce.dto.OrderDTO;
import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.dto.UserDTO;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.User;

public class UserMapper {
    public static UserDTO toDto(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        List<CartDTO> cartDTOs = user.getCarts() != null
            ? user.getCarts().stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList())
            : new ArrayList<>();
        dto.setCarts(cartDTOs);

        List<OrderDTO> orderDTOs = user.getOrders() != null
            ? user.getOrders().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList())
            : new ArrayList<>();
        dto.setOrders(orderDTOs);
        return dto;
    }

    public static User toEntity(UserDTO UserDTO){
        User dto = new User();
        dto.setId(UserDTO.getId());
        dto.setName(UserDTO.getName());
        dto.setEmail(UserDTO.getEmail());
        dto.setPassword(UserDTO.getPassword());
        dto.setRole(UserDTO.getRole());

        // List<CartDTO> cartDTOs = UserDTO.getCarts() != null
        //     ? UserDTO.getCarts()
        //     : new ArrayList<>();
        
        // List<Cart> carts = cartDTOs.stream()
        //     .map(UserMapper::toMapCart)
        //     .collect(Collectors.toList());
        // dto.setCarts(carts);

        // List<OrderDTO> orderDTOs = UserDTO.getOrders() != null
        //     ? UserDTO.getOrders()
        //     : new ArrayList<>();

        // List<Order> orders = orderDTOs.stream()
        //     .map(UserMapper::toMapOrder)
        //     .collect(Collectors.toList());
        // dto.setOrders(orders);
        return dto;
    }

    public static Order toMapOrder(OrderDTO orderDTO){
        Order dto = new Order();
        dto.setId(orderDTO.getId());
        dto.setTotalPrice(orderDTO.getTotalPrice());
        dto.setOrderDate(orderDTO.getOrderDate());
        dto.setStatus(orderDTO.getStatus());
        dto.setPaymentInfo(orderDTO.getPaymentInfo());

        // List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems() != null
        //     ? orderDTO.getOrderItems()
        //     : new ArrayList<>();
        
        // List<OrderItem> orderItems = orderItemDTOs.stream()
        //     .map(OrderMapper::toMapOrderItem)
        //     .collect(Collectors.toList());
        dto.setOrderItems(new ArrayList<>());
        return dto;
    }

    // public static Cart toMapCart(CartDTO cartDTO){
    //     Cart dto = new Cart();
    //     dto.setId(cartDTO.getId());
    //     List<CartItemDTO> cartItemDTOs = cartDTO.getCartItems() != null
    //         ? cartDTO.getCartItems()
    //         : new ArrayList<>();
    //     List<CartItem> cartItems = cartItemDTOs.stream()
    //             .map(CartMapper::toMapCartItem)
    //             .collect(Collectors.toList());
    //     dto.setCartItems(cartItems);
    //     return dto;
    // }
}
