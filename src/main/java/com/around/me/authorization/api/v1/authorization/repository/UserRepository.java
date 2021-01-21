package com.around.me.authorization.api.v1.authorization.repository;


import com.around.me.authorization.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);
}
