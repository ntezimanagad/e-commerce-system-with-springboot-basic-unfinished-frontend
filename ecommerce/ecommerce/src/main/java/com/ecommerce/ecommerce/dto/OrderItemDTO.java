package com.ecommerce.ecommerce.dto;

public class OrderItemDTO {
    private Long id;
    private Long order_id;
    private Long product_id;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private Double priceAtPurchase;
    public OrderItemDTO() {
    }

    

    public OrderItemDTO(Long order_id, Long product_id, String productName, Double productPrice, Integer quantity,
            Double priceAtPurchase) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }



    public String getProductName() {
        return productName;
    }



    public void setProductName(String productName) {
        this.productName = productName;
    }



    public Double getProductPrice() {
        return productPrice;
    }



    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getPriceAtPurchase() {
        return priceAtPurchase;
    }
    public void setPriceAtPurchase(Double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
    
}
