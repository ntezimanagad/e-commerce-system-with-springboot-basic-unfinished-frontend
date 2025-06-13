package com.ecommerce.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.OrderDTO;
import com.ecommerce.ecommerce.mapper.OrderMapper;
import com.ecommerce.ecommerce.model.Cart;
import com.ecommerce.ecommerce.model.CartItem;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.User;
import com.ecommerce.ecommerce.repository.CartItemRepository;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.OrderItemRepository;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<OrderDTO> getAllByPage(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(OrderMapper::toDto);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toEntity(orderDTO);
        if (orderDTO.getUser_id() != null) {
            User user = userRepository.findById(orderDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Id not found"));
            order.setUser(user);
        }
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Id not found");
        }
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderDate(orderDTO.getOrderDate());
            order.setPaymentInfo(orderDTO.getPaymentInfo());
            order.setStatus(orderDTO.getStatus());
            order.setTotalPrice(orderDTO.getTotalPrice());
            Order saved = orderRepository.save(order);
            return OrderMapper.toDto(saved);
        } else {
            throw new RuntimeException("Id not found");
        }
    }

    public OrderDTO createOrderFromCart(Long cartId) {
    // 1. Fetch the cart
    Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

    // 2. Fetch cart items from the cart
    List<CartItem> cartItems = cart.getCartItems();
    if (cartItems == null || cartItems.isEmpty()) {
        throw new RuntimeException("Cart is empty");
    }

    // 3. Create the order
    Order order = new Order();
    order.setUser(cart.getUser());
    order.setOrderDate(new java.sql.Date(new java.util.Date().getTime()));
    order.setStatus("PENDING");

    double total = 0.0;
    List<OrderItem> orderItems = new ArrayList<>();

    for (CartItem cartItem : cartItems) {
        Product product = cartItem.getProduct();

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order); // establish relationship
        orderItem.setProduct(product);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPriceAtPurchase(product.getPrice());

        total += product.getPrice() * cartItem.getQuantity();
        orderItems.add(orderItem);
    }

    order.setOrderItems(orderItems);
    order.setTotalPrice(total);

    // 4. Save order first to get order ID
    Order savedOrder = orderRepository.save(order);

    // 5. Save order items manually
    for (OrderItem item : orderItems) {
        item.setOrder(savedOrder); // ensure linkage with the persisted order
        orderItemRepository.save(item); // explicitly save
    }

    // 6. Clear cart items
    cartItemRepository.deleteAll(cartItems);

    // 7. Convert to DTO
    return OrderMapper.toDto(savedOrder);
}



}
