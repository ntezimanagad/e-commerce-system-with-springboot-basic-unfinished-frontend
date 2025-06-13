package com.ecommerce.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.mapper.OrderItemMapper;
import com.ecommerce.ecommerce.model.Order;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.repository.OrderItemRepository;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<OrderItemDTO> getAll(){
        return orderItemRepository.findAll()
            .stream()
            .map(OrderItemMapper::toDto)
            .collect(Collectors.toList());
    }

    public Page<OrderItemDTO> getAllByPage(Pageable pageable){
        return orderItemRepository.findAll(pageable)
            .map(OrderItemMapper::toDto);
    }

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO){
        OrderItem orderItem = OrderItemMapper.toEntity(orderItemDTO);
        if (orderItemDTO.getOrder_id() != null) {
            Order order = orderRepository.findById(orderItemDTO.getOrder_id())
                .orElseThrow(()-> new RuntimeException("Id not found"));
            orderItem.setOrder(order);
        }
        if (orderItemDTO.getProduct_id() != null) {
            Product product = productRepository.findById(orderItemDTO.getProduct_id())
                .orElseThrow(()-> new RuntimeException("Id not found"));
            orderItem.setProduct(product);
        }
        OrderItem saved = orderItemRepository.save(orderItem);
        return OrderItemMapper.toDto(saved);
    }

    public void deleteOrderItem(Long id){
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
        }else{
            throw new RuntimeException("id not found");
        }
    }

    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO, Long id){
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
        if (optionalOrderItem.isPresent()) {
            OrderItem oi = optionalOrderItem.get();
            oi.setPriceAtPurchase(orderItemDTO.getPriceAtPurchase());
            oi.setQuantity(orderItemDTO.getQuantity());
            OrderItem saved = orderItemRepository.save(oi);
            return OrderItemMapper.toDto(saved);
        }else{
            throw new RuntimeException("Id not found");
        }
    }
}
