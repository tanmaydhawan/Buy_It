package com.tanmay.buyit.config;

import com.tanmay.buyit.entity.Roles;
import com.tanmay.buyit.entity.User;
import com.tanmay.buyit.repo.RoleRepository;
import com.tanmay.buyit.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    private static final String ADMIN = "BUYIT_ADMIN";
    private static final String CUSTOMER = "BUYIT_CUSTOMER";
    private static final String ADMIN_EMAIL = "admin@buyit.com";
    private static final String ADMIN_FIRST_NAME = "Tanmay";
    private static final String ADMIN_LAST_NAME = "Dhawan";
    private static final String ADMIN_PASSWORD = "password";

    @Override
    public void run(String... args) throws Exception {
        Roles adminRole = createRoleIfNotPresent(ADMIN);
        Roles customerRole = createRoleIfNotPresent(CUSTOMER);

        createAdminIfNotPresent(customerRole, adminRole);
    }

    private void createAdminIfNotPresent(Roles admin, Roles customer) {
        userRepository.findByEmail(ADMIN_EMAIL)
                .orElseGet(()-> userRepository.save(
                        User.builder()
                                .firstName(ADMIN_FIRST_NAME)
                                .lastName(ADMIN_LAST_NAME)
                                .email(ADMIN_EMAIL)
                                .password(ADMIN_PASSWORD)
                                .userRoles(Set.of(admin, customer))
                                .build()
                ));
    }

    private Roles createRoleIfNotPresent(String role) {
        return roleRepository.findByName(role)
                .orElseGet(() -> roleRepository.save(
                        Roles.builder()
                                .name(role)
                                .build()
                ));
    }
}
