package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.RegisterUserRequest;
import com.tanmay.buyit.dto.RegisterUserResponse;
import com.tanmay.buyit.entity.Roles;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.repo.RoleRepository;
import com.tanmay.buyit.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegisterUserResponse userSignup (RegisterUserRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("User with this email already exists!");
        }

        //Default Role
        Roles customerRole = roleRepository.findByName("BUYIT_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRoles(Set.of(customerRole));

        User savedUser = userRepository.save(user);

        return new RegisterUserResponse(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), LocalDateTime.now());
    }
}
