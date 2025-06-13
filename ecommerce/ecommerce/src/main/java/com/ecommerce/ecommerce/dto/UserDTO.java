package com.ecommerce.ecommerce.dto;

import java.util.List;

import com.ecommerce.ecommerce.model.Role;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private List<CartDTO> carts;
    private List<OrderDTO> orders;
    private String otpCode;
    public UserDTO() {
    }
    
    public UserDTO(String name, String email, String password, Role role, List<CartDTO> carts, List<OrderDTO> orders) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.carts = carts;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }
    public List<CartDTO> getCarts() {
        return carts;
    }

    public void setCarts(List<CartDTO> carts) {
        this.carts = carts;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    
}
