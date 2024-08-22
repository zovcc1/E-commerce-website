package com.website.e_commerce.product.model.entity;

import com.website.e_commerce.cart.model.entity.Cart;
import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.product.model.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Data
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "unit_cost")
    private float unitCost;
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;
    private int rate;
    @ManyToMany(mappedBy = "products")
    private Set<CustomerEntity> customers = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "cart_item",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id")
    )
    private Set<Cart> carts = new HashSet<>();

    public void addToCart() {
        // Implementation
    }

}
