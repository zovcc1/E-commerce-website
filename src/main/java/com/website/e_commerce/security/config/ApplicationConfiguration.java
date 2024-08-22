package com.website.e_commerce.security.config;

import com.website.e_commerce.customer.repository.CustomerEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Configuration class for setting up security-related beans in the application.
 *
 * This class provides the necessary configuration for user authentication and password encoding
 * using Spring Security. It defines beans for user details service, password encoder,
 * authentication manager, and authentication provider.
 *
 * <p>
 * The {@code ApplicationConfiguration} class is annotated with {@code @Configuration},
 * indicating that it contains bean definitions and is used by Spring to generate the application context.
 * </p>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *   <li>{@link CustomerEntityRepository}: Repository to fetch user details from the database.</li>
 * </ul>
 *
 * <h2>Bean Definitions:</h2>
 * <ul>
 *   <li>{@code UserDetailsService} - {@code loadUserDetails()}:
 *     Provides user details based on the username. It retrieves user information from the repository
 *     and throws {@code UsernameNotFoundException} if the user is not found.</li>
 *   <li>{@code PasswordEncoder} - {@code passwordEncoder()}:
 *     Provides a {@code BCryptPasswordEncoder} for encoding passwords securely.</li>
 *   <li>{@code AuthenticationManager} - {@code authenticationManager(AuthenticationConfiguration config)}:
 *     Returns the {@code AuthenticationManager} from the provided {@code AuthenticationConfiguration}.</li>
 *   <li>{@code AuthenticationProvider} - {@code authenticationProvider()}:
 *     Configures a {@code DaoAuthenticationProvider} that uses {@code UserDetailsService} and
 *     {@code PasswordEncoder} to authenticate users.</li>
 * </ul>
 *
 * <h2>Constructor:</h2>
 * <ul>
 *   <li>{@code ApplicationConfiguration(CustomerEntityRepository customerEntityRepository)}:
 *     Constructs an instance with the provided {@code CustomerEntityRepository}.</li>
 * </ul>
 *
 * @see CustomerEntityRepository
 * @see BCryptPasswordEncoder
 * @see DaoAuthenticationProvider
 * @see AuthenticationConfiguration
 * @see AuthenticationManager
 * @see UserDetailsService
 * @see PasswordEncoder
 * @see UsernameNotFoundException
 */

@Configuration
public class ApplicationConfiguration {
    private final CustomerEntityRepository customerEntityRepository;

    public ApplicationConfiguration(CustomerEntityRepository customerEntityRepository) {
        this.customerEntityRepository = customerEntityRepository;
    }
    @Bean
    UserDetailsService loadUserDetails(){
        return username -> customerEntityRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(loadUserDetails());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
}
