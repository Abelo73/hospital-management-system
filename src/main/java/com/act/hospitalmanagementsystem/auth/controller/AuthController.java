package com.act.hospitalmanagementsystem.auth.controller;

import com.act.hospitalmanagementsystem.auth.dto.LoginRequest;
import com.act.hospitalmanagementsystem.auth.dto.LoginResponse;
import com.act.hospitalmanagementsystem.auth.dto.RegisterRequest;
import com.act.hospitalmanagementsystem.auth.service.AuthService;
import com.act.hospitalmanagementsystem.common.dto.BaseResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${app.cookie.same-site:lax}")
    private String cookieSameSite;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authService.register(request);
        setAuthCookies(response, loginResponse);
        return ResponseEntity.ok(BaseResponseDTO.success("Registration successful", loginResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request);
        setAuthCookies(response, loginResponse);
        return ResponseEntity.ok(BaseResponseDTO.success("Login successful", loginResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponseDTO<LoginResponse>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).body(BaseResponseDTO.error("Refresh token not found", "REFRESH_TOKEN_MISSING"));
        }
        LoginResponse loginResponse = authService.refreshToken(refreshToken);
        setAuthCookies(response, loginResponse);
        return ResponseEntity.ok(BaseResponseDTO.success("Token refreshed successfully", loginResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponseDTO<Void>> logout(HttpServletResponse response) {
        clearAuthCookies(response);
        return ResponseEntity.ok(BaseResponseDTO.success("Logged out successfully", null));
    }

    private void setAuthCookies(HttpServletResponse response, LoginResponse loginResponse) {
        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(cookieSecure);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(3600); // 1 hour
        accessTokenCookie.setAttribute("SameSite", cookieSameSite);

        Cookie refreshTokenCookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(cookieSecure);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(604800); // 7 days
        refreshTokenCookie.setAttribute("SameSite", cookieSameSite);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void clearAuthCookies(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", "");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(cookieSecure);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setAttribute("SameSite", cookieSameSite);

        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(cookieSecure);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setAttribute("SameSite", cookieSameSite);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}
