package com.tanmay.buyit.controller;


import com.tanmay.buyit.dto.LoginRequest;
import com.tanmay.buyit.dto.LoginResponse;
import com.tanmay.buyit.security.JwtService;
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

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequest request){

        try{
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
            String jwtToken = jwtService.createJwtToken((UserDetails) authenticate.getPrincipal());
            return ResponseEntity.ok(jwtToken);
        }
        catch (Exception e){   //Temperary Impl, To be fixed using real DTOs
            log.debug("Username/Password might be Incorrect!");
            return new ResponseEntity<>("Incorrect Username / Password", HttpStatus.BAD_REQUEST);
        }
    }
}
