package com.example.app3.m2.repo;

import com.example.app3.m2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
