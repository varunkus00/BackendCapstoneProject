package com.ecommerce.userauthenticationservice.repos;

import com.ecommerce.userauthenticationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
