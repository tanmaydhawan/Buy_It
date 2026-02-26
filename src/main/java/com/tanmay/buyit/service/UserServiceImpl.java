package com.tanmay.buyit.service;

import com.tanmay.buyit.dto.RegisterUserRequest;
import com.tanmay.buyit.dto.RegisterUserResponse;
import com.tanmay.buyit.entity.Roles;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.exception.RoleNotFoundException;
import com.tanmay.buyit.exception.UserAlreadyExistsException;
import com.tanmay.buyit.repo.RoleRepository;
import com.tanmay.buyit.repo.UserRepository;
import jakarta.transaction.Transactional;
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

    private static final String CUSTOMER_ROLE = "BUYIT_CUSTOMER";

    @Transactional
    public RegisterUserResponse userSignup (RegisterUserRequest request){

        checkUserDoesNotExists(request.getEmail());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoles(Set.of(setDefaultRole()))
                .build();

        User savedUser = userRepository.save(user);

        return new RegisterUserResponse(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), LocalDateTime.now());
    }

    private void  checkUserDoesNotExists(String email){
        if(userRepository.existsByEmail(email)){
            throw new UserAlreadyExistsException(email);
        }
    }

    private Roles setDefaultRole(){
        return roleRepository.findByName(CUSTOMER_ROLE)
                .orElseThrow(() -> new RoleNotFoundException(CUSTOMER_ROLE));
    }
}
