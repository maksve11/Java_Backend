package com.maksvell.repository;

import org.springframework.stereotype.Repository;
import com.maksvell.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
