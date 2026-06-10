package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.LoginRequest;
import com.act.hospitalmanagementsystem.auth.dto.LoginResponse;
import com.act.hospitalmanagementsystem.auth.dto.RegisterRequest;
import com.act.hospitalmanagementsystem.auth.service.AuthService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("new@example.com");
        registerRequest.setFirstName("New");
        registerRequest.setLastName("User");

        loginResponse = new LoginResponse();
        loginResponse.setAccessToken("accessToken");
        loginResponse.setRefreshToken("refreshToken");
        loginResponse.setUsername("testuser");
        loginResponse.setEmail("test@example.com");
        loginResponse.setFirstName("Test");
        loginResponse.setLastName("User");
    }

    @Test
    void testLogin_Success() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.data.refreshToken").value("refreshToken"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testRegister_Success() throws Exception {
        when(authService.register(any(RegisterRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"));
    }

    @Test
    void testRegister_ValidationError() throws Exception {
        registerRequest.setUsername(""); // Invalid username

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogout_Success() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }
}
