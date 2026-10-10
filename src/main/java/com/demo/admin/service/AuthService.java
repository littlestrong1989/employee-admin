package com.demo.admin.service;

import com.demo.admin.common.BizException;
import com.demo.admin.dto.LoginRequest;
import com.demo.admin.dto.LoginResponse;
import com.demo.admin.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest req) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
            String role = auth.getAuthorities().stream()
                    .findFirst().map(a -> a.getAuthority()).orElse("ROLE_USER");
            String token = jwtUtil.generate(req.getUsername(), Map.of("role", role));
            return new LoginResponse(token, req.getUsername(), role);
        } catch (BadCredentialsException e) {
            throw new BizException(401, "用户名或密码错误");
        }
    }
}
