package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserAuth;
import com.example.demo.repository.UserAuthRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserAuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @GetMapping("/userauths")
    public List<UserAuth> getAllUserAuths() {
        return userAuthRepository.findAll();
    }

    @PostMapping("/userauths")
    public UserAuth createUserAuth(@Validated @RequestBody UserAuth newUserAuth) {
        return userAuthRepository.save(newUserAuth);
    }

    @PutMapping("/userauths/{id}")
    public ResponseEntity<UserAuth> updateUserAuth(@PathVariable(value = "id") Long userId,
                                                   @Validated @RequestBody UserAuth updatedUserAuth) throws ResourceNotFoundException {
        UserAuth userAuth = userAuthRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth not found for this id :: " + userId));

        userAuth.setCustomer(updatedUserAuth.getCustomer());
        userAuth.setUsername(updatedUserAuth.getUsername());
        userAuth.setPassword(updatedUserAuth.getPassword());
        // Set other fields you want to update

        userAuthRepository.save(userAuth);

        return ResponseEntity.ok(userAuth);
    }

    @DeleteMapping("/userauths/{id}")
    public Map<String, Boolean> deleteUserAuth(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        UserAuth userAuth = userAuthRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth not found for this id :: " + userId));

        userAuthRepository.delete(userAuth);
        Map<String, Boolean> response = new HashMap<>();
        response.put("UserAuth has been Deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/userauths/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        UserAuth userAuth = userAuthRepository.findByUsername(username);

        if (userAuth != null && userAuth.getPassword().equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Authentication successful");
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
