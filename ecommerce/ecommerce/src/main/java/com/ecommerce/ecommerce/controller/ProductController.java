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

import com.ecommerce.ecommerce.dto.ProductDTO;
import com.ecommerce.ecommerce.service.ProductService;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/get")
    public ResponseEntity<List<ProductDTO>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<ProductDTO>> getAllByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> page2 = productService.getAllByPage(pageable);
        return ResponseEntity.ok(page2); 
    }
    @PostMapping(value = "/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO){
        productService.createProduct(productDTO);
        return ResponseEntity.ok("created");
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted");
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id){
        productService.updateProduct(productDTO, id);
        return ResponseEntity.ok("updated");
    }
}
