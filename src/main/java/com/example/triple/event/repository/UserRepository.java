package com.example.triple.event.repository;


import com.example.triple.event.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userId);
}
