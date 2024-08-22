package com.website.e_commerce.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, CorsConfigurationSource corsConfigurationSource) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    public RequestMatcher publicEndpointMatcher(){
       return new OrRequestMatcher(
               new AntPathRequestMatcher("/auth/authenticate" , "POST"),
               new AntPathRequestMatcher("auth/signin" , "GET"),
               new AntPathRequestMatcher("auth/register" , "POST"),
               new AntPathRequestMatcher("auth/signup" , "GET"),
               new AntPathRequestMatcher("/static/**" , "GET")

       );



    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer
                                .FrameOptionsConfig::disable))

                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/static/**").permitAll(); // Allow access to static resources
                    auth.requestMatchers("/auth/**").permitAll(); // Allow access to the signup page
//                    auth.requestMatchers("/auth/login").permitAll(); // Allow access to the login page
                    auth.anyRequest().authenticated(); // Protect all other request

                })
//                .formLogin(formlogin -> formlogin
//                        .loginPage("/auth/signin") // Custom login page URL
//                        .loginProcessingUrl("/auth/authenticate")
//                        .defaultSuccessUrl("/home", true) // Redirect to home page on success
//                        .failureUrl("/auth/signin?error=true")// Redirect on failure
//                        .permitAll())
//
//                .logout(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf(AbstractHttpConfigurer::disable)
//            .cors(corsConfigurer ->corsConfigurer.configure(http))
//            .authorizeHttpRequests(auth -> {
//                auth.requestMatchers("/h2-console/**", "/auth/**").permitAll();
//                auth.anyRequest().authenticated();
//            })
//            .formLogin(formLogin -> formLogin
//                    .loginPage("/auth/login") // Optional: Customize the login page URL
//                    .permitAll())
//            .sessionManagement(session -> session
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authenticationProvider(authenticationProvider)
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//}




    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
