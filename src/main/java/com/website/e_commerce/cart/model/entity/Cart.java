package com.website.e_commerce.cart.model.entity;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    public void updateQuantity() {
        // Implementation
    }

    public void cartDetails() {
        // Implementation
    }

    public void placeOrder() {
        // Implementation
    }


}
