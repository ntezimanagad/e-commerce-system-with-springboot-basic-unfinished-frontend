package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.OrderDTO;
import com.ecommerce.ecommerce.service.OrderService;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<OrderDTO>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> page2 = orderService.getAllByPage(pageable);
        return ResponseEntity.ok(page2);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable Long id) {
        orderService.updateOrder(orderDTO, id);
        return ResponseEntity.ok("updated");
    }

    @PostMapping("/from-cart/{cartId}")
    public ResponseEntity<OrderDTO> createOrderFromCart(@PathVariable Long cartId) {
        OrderDTO orderDTO = orderService.createOrderFromCart(cartId);
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkoutCart(@RequestParam Long cartId) {
        OrderDTO orderDTO = orderService.createOrderFromCart(cartId);
        return ResponseEntity.ok(orderDTO);
    }

}
