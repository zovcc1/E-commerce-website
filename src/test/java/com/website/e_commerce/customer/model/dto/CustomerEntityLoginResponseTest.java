package com.website.e_commerce.customer.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityLoginResponseTest {

    @Test
    void getToken() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setToken("sampleToken");
        assertEquals("sampleToken", response.getToken());
    }

    @Test
    void getExpiresAt() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setExpiresAt(123456789L);
        assertEquals(123456789L, response.getExpiresAt());
    }

    @Test
    void setToken() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setToken("newToken");
        assertEquals("newToken", response.getToken());
    }

    @Test
    void setExpiresAt() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setExpiresAt(987654321L);
        assertEquals(987654321L, response.getExpiresAt());
    }

    @Test
    void testEquals() {
        CustomerEntityLoginResponse response1 = new CustomerEntityLoginResponse();
        response1.setToken("token");
        response1.setExpiresAt(123L);

        CustomerEntityLoginResponse response2 = new CustomerEntityLoginResponse();
        response2.setToken("token");
        response2.setExpiresAt(123L);

        assertEquals(response1, response2);
    }

    @Test
    void canEqual() {
        CustomerEntityLoginResponse response1 = new CustomerEntityLoginResponse();
        CustomerEntityLoginResponse response2 = new CustomerEntityLoginResponse();
        assertTrue(response1.canEqual(response2));
    }

    @Test
    void testHashCode() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setToken("token");
        response.setExpiresAt(123L);
        assertNotNull(response.hashCode());
    }

    @Test
    void testToString() {
        CustomerEntityLoginResponse response = new CustomerEntityLoginResponse();
        response.setToken("token");
        response.setExpiresAt(123L);
        assertNotNull(response.toString());
    }

    @Test
    void builder() {
        CustomerEntityLoginResponse response = CustomerEntityLoginResponse.builder()
                .token("token")
                .expiresAt(123L)
                .build();
        assertEquals("token", response.getToken());
        assertEquals(123L, response.getExpiresAt());
    }
}
