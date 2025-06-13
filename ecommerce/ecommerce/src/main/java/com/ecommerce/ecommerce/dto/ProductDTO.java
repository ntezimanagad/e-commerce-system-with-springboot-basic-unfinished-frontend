package com.ecommerce.ecommerce.dto;

import java.util.List;

public class ProductDTO {
    private Long id;
    private String name;
    private String descrption;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private List<CartItemDTO> cartItems;
    private List<OrderItemDTO> orderItems;
    public ProductDTO() {
    }

    public ProductDTO(String name, String descrption, Double price, Integer stock, String imageUrl,
            List<CartItemDTO> cartItems, List<OrderItemDTO> orderItems) {
        this.name = name;
        this.descrption = descrption;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.cartItems = cartItems;
        this.orderItems = orderItems;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescrption() {
        return descrption;
    }
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
  
}
