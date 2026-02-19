package com.tanmay.buyit.controller;


import com.tanmay.buyit.dto.LoginRequest;
import com.tanmay.buyit.dto.LoginResponse;
import com.tanmay.buyit.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        String jwtToken = jwtService.createJwtToken((UserDetails) authenticate.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }
}
