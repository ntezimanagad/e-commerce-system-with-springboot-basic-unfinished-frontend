package com.ecommerce.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.dto.ProductDTO;
import com.ecommerce.ecommerce.mapper.ProductMapper;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll(){
        return productRepository.findAll()
            .stream()
            .map(ProductMapper::toDto)
            .collect(Collectors.toList());
    }
    public Page<ProductDTO> getAllByPage(Pageable pageable){
        return productRepository.findAll(pageable)
            .map(ProductMapper::toDto);
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Optional<Product> optionalProduct = productRepository.findByName(productDTO.getName());
        if (optionalProduct.isPresent()) {
            throw new RuntimeException("Product Already Exists");
        }
        Product product = ProductMapper.toEntity(productDTO);
        Product saved = productRepository.save(product);
        return ProductMapper.toDto(saved);
    }

    public void deleteProduct(Long id){
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new RuntimeException("User not found");
        }
    }
    public ProductDTO updateProduct(ProductDTO productDTO, Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product pro = optionalProduct.get();
            pro.setName(productDTO.getName());
            pro.setPrice(productDTO.getPrice());
            pro.setDescrption(productDTO.getDescrption());
            pro.setStock(productDTO.getStock());
            pro.setImageUrl(productDTO.getImageUrl());
            Product saved = productRepository.save(pro);
            return ProductMapper.toDto(saved);
        }else{
            throw new RuntimeException("Id not Found");
        }
    }
}
