package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.LoginRequest;
import com.act.hospitalmanagementsystem.auth.dto.LoginResponse;
import com.act.hospitalmanagementsystem.auth.dto.RegisterRequest;
import com.act.hospitalmanagementsystem.auth.service.AuthService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> register(@Valid @RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(BaseResponseDTO.success("Login successful", response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.replace("Bearer ", "");
        LoginResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(BaseResponseDTO.success("Token refreshed successfully", response));
    }
}
