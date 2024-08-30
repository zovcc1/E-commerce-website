package com.website.e_commerce.order.model.entity;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "orders")

public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Temporal(TemporalType.DATE)
    private Date shippedDate;

    @ManyToOne
    @JoinColumn(name = "customer_id" , nullable = false)
    private CustomerEntity customer;

    private String status;


    public void paymentGateway() {
        // Implementation
    }

    public void orderDetails() {
        // Implementation
    }
}
