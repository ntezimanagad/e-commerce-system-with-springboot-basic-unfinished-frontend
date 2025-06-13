package com.ecommerce.ecommerce.dto;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long user_id;
    private Date orderDate;
    private String status;
    private Double totalPrice;
    private String paymentInfo;
    private List<OrderItemDTO> orderItems;
    public OrderDTO() {
    }
    
    public OrderDTO(Long user_id, Date orderDate, String status, Double totalPrice, String paymentInfo,
            List<OrderItemDTO> orderItems) {
        this.user_id = user_id;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.paymentInfo = paymentInfo;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getPaymentInfo() {
        return paymentInfo;
    }
    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
    
}
