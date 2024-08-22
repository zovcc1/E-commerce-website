package com.website.e_commerce.customer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.website.e_commerce.annotation.Password;
import com.website.e_commerce.order.model.entity.OrderEntity;
import com.website.e_commerce.product.model.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code CustomerEntity} class represents a customer in the e-commerce application.
 * This entity is mapped to a database table and includes fields such as first name,
 * last name, email, and password, with appropriate validation constraints.
 *
 * <p>The class is annotated with JPA annotations to define the entity and its fields.
 * Additionally, it implements the {@link UserDetails} interface, making it compatible
 * with Spring Security for authentication and authorization purposes.</p>
 *
 * <p>Lombok annotations are used to reduce boilerplate code by automatically generating
 * getters, setters, constructors, and other methods. The {@code @Builder} annotation
 * facilitates the builder pattern for creating instances of this class.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><strong>id:</strong> The primary key for the customer entity, generated using a sequence strategy.</li>
 *   <li><strong>firstName:</strong> The customer's first name, which is mandatory and cannot be blank.</li>
 *   <li><strong>lastName:</strong> The customer's last name, which is also mandatory and cannot be blank.</li>
 *   <li><strong>email:</strong> The customer's email address, which must be unique, well-formed, and non-blank.</li>
 *   <li><strong>password:</strong> The customer's password, validated by custom constraints defined by the {@code @Password} annotation.</li>
 * </ul>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>{@link #getAuthorities()}: Returns the authorities granted to the user. Currently returns an empty list.</li>
 *   <li>{@link #getPassword()}: Returns the password used to authenticate the user.</li>
 *   <li>{@link #getUsername()}: Returns the email address, which serves as the username for authentication.</li>
 *   <li>{@link #isAccountNonExpired()}: Indicates whether the user's account has expired. Always returns {@code true}.</li>
 *   <li>{@link #isAccountNonLocked()}: Indicates whether the user is locked or unlocked. Currently always returns {@code true}.</li>
 *   <li>{@link #isCredentialsNonExpired()}: Indicates whether the user's credentials (password) have expired. Returns {@code false} by default.</li>
 *   <li>{@link #isEnabled()}: Indicates whether the user is enabled or disabled. Returns {@code false} by default.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CustomerEntity customer = CustomerEntity.builder()
 *     .firstName("John")
 *     .lastName("Doe")
 *     .email("john.doe@example.com")
 *     .password("SecurePassword123!")
 *     .build();
 * }</pre>
 *
 * @see UserDetails
 * @see jakarta.persistence.Entity
 * @see lombok.Builder
 * @see com.website.e_commerce.annotation.Password
 */


@Data
@Entity
@Table(name = "customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name" , nullable = false )
    @NotBlank(message = "first name is a mandatory")
    private String firstName;

    @NotBlank(message = "last name is mandatory")
    @Column(name = "last_name" , nullable = false)
    private String lastName;
    @NotBlank(message = "email is a mandatory")
    @Column(name = "email" , unique = true , nullable = false)
    @Email(message = "must be a well-formed email address")
    private String email;
    @NotBlank(message = "password is a mandatory")
    @Password
    private String password;
    @NotBlank(message = "address is a mandatory")
    private String address;
    @NotBlank(message = "phone number is a mandatory")
    private String phoneNumber;

     @ManyToMany
     @JsonIgnore
     @JoinTable(
             name = "customer_product" ,
             joinColumns = @JoinColumn(name = "customer_id"),
             inverseJoinColumns = @JoinColumn(name = "product_id")
     )
     private Set<Product> products = new HashSet<>();
     @JsonIgnore
     @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
     private Set<OrderEntity> orderEntities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public @NonNull String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //Todo make lock feature in the future
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
