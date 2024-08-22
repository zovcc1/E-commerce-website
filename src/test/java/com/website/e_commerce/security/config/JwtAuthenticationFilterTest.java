package com.website.e_commerce.security.config;

import com.website.e_commerce.customer.model.entity.CustomerEntity;
import com.website.e_commerce.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JwtAuthenticationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JwtAuthenticationFilterTest {
    @MockBean
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    /**
     * Method under test:
     * {@link JwtAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    }

    /**
     * Method under test:
     * {@link JwtAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal2() throws ServletException, IOException {
        // Arrange
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        verify(request).getHeader(eq("Authorization"));
    }

    /**
     * Method under test:
     * {@link JwtAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal3() throws ServletException, IOException, UsernameNotFoundException {
        // Arrange
        when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(new CustomerEntity());
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).extractUsername(eq(""));
        verify(jwtService).isTokenValid(eq(""), isA(UserDetails.class));
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        verify(request).getRemoteAddr();
        verify(request).getHeader(eq("Authorization"));
        verify(request).getSession(eq(false));
        verify(userDetailsService).loadUserByUsername(eq("janedoe"));
    }

    /**
     * Method under test:
     * {@link JwtAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal4() throws ServletException, IOException, UsernameNotFoundException {
        // Arrange
        when(userDetailsService.loadUserByUsername(Mockito.<String>any())).thenReturn(null);
        when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");
        when(handlerExceptionResolver.resolveException(Mockito.<HttpServletRequest>any(),
                Mockito.<HttpServletResponse>any(), Mockito.<Object>any(), Mockito.<Exception>any()))
                .thenReturn(new ModelAndView("View Name"));
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, new Response(), mock(FilterChain.class));

        // Assert
        verify(jwtService).extractUsername(eq(""));
        verify(jwtService).isTokenValid(eq(""), isNull());
        verify(request).getHeader(eq("Authorization"));
        verify(userDetailsService).loadUserByUsername(eq("janedoe"));
        verify(handlerExceptionResolver).resolveException(isA(HttpServletRequest.class), isA(HttpServletResponse.class),
                isNull(), isA(Exception.class));
    }
}
