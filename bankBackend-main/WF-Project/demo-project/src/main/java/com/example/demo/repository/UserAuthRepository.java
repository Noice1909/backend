package com.example.demo.repository;

import com.example.demo.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long>{


    UserAuth findByUsernameAndPassword(String username, String password);

    UserAuth findByUsername(String username);
}