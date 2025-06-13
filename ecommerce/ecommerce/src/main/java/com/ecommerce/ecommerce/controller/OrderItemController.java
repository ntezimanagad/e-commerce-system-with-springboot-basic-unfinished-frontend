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

import com.ecommerce.ecommerce.dto.OrderItemDTO;
import com.ecommerce.ecommerce.model.OrderItem;
import com.ecommerce.ecommerce.repository.OrderItemRepository;
import com.ecommerce.ecommerce.service.OrderItemService;

@RestController
@RequestMapping(value = "/api/OrderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping(value = "/get")
    public ResponseEntity<List<OrderItemDTO>> getAll() {
        return ResponseEntity.ok(orderItemService.getAll());
    }

    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<List<OrderItem>> getByOrderId(@PathVariable Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        items.forEach(item -> {
            item.setProduct(item.getProduct()); // force load if lazy
        });
        return ResponseEntity.ok(items);
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<OrderItemDTO>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItemDTO> page2 = orderItemService.getAllByPage(pageable);
        return ResponseEntity.ok(page2);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        orderItemService.createOrderItem(orderItemDTO);
        return ResponseEntity.ok("created");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateOrderItem(@RequestBody OrderItemDTO orderItemDTO, @PathVariable Long id) {
        orderItemService.updateOrderItem(orderItemDTO, id);
        return ResponseEntity.ok("updated");
    }
}
