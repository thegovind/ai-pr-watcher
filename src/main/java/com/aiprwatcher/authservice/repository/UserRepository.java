package com.aiprwatcher.authservice.repository;

import com.aiprwatcher.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByAccountNumber(String accountNumber);
    
    boolean existsByAccountNumber(String accountNumber);
}
