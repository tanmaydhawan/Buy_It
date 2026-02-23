package com.tanmay.buyit.controller;


import com.tanmay.buyit.dto.LoginRequest;
import com.tanmay.buyit.dto.LoginResponse;
import com.tanmay.buyit.dto.RegisterUserRequest;
import com.tanmay.buyit.dto.RegisterUserResponse;
import com.tanmay.buyit.security.JwtService;
import com.tanmay.buyit.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserServiceImpl userService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
            String jwtToken = jwtService.createJwtToken((UserDetails) authenticate.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterUserResponse> signUp (@Valid @RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse registerUserResponse = userService.userSignup(registerUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerUserResponse);
    }
}
