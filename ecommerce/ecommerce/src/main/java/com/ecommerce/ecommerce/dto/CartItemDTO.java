package com.ecommerce.ecommerce.dto;

public class CartItemDTO {
    private Long id;
    private Long cart_id;
    private Long product_id;
    private Integer quantity;
    public CartItemDTO() {
    }
    
    public CartItemDTO(Long cart_id, Long product_id, Integer quantity) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
}
