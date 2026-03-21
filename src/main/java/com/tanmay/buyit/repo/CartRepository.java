package com.tanmay.buyit.repo;

import com.tanmay.buyit.entity.Cart;
import com.tanmay.buyit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
