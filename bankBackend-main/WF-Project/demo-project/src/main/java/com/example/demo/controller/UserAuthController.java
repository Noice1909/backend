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
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class UserAuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @GetMapping("/user-auth")
    public List<UserAuth> getAllUserAuths() {
        return userAuthRepository.findAll();
    }

    @PostMapping("/user-auth")
    public ResponseEntity<Map<String, Object>> createUserAuth(@Validated @RequestBody UserAuth newUserAuth) {
        UserAuth createdUserAuth = userAuthRepository.save(newUserAuth);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "UserAuth created successfully");
        response.put("userAuth", createdUserAuth); // Include the created UserAuth in the response

        return ResponseEntity.ok(response);
    }

    @PutMapping("/user-auth/{id}")
    public ResponseEntity<UserAuth> updateUserAuth(@PathVariable(value = "id") Long userId,
                                                   @Validated @RequestBody UserAuth updatedUserAuth) throws ResourceNotFoundException {
        UserAuth userAuth = userAuthRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth not found for this id :: " + userId));

        userAuth.setCustomer(updatedUserAuth.getCustomer());
        userAuth.setUsername(updatedUserAuth.getUsername());
        userAuth.setPassword(updatedUserAuth.getPassword());

        userAuthRepository.save(userAuth);

        return ResponseEntity.ok(userAuth);
    }

    @DeleteMapping("/user-auth/{id}")
    public Map<String, Boolean> deleteUserAuth(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        UserAuth userAuth = userAuthRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserAuth not found for this id :: " + userId));

        userAuthRepository.delete(userAuth);
        Map<String, Boolean> response = new HashMap<>();
        response.put("UserAuth has been Deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/user-auth-login")
    public ResponseEntity<Map<String, Object>> validateLogin(@Validated @RequestBody UserAuth loginIDPass) {
        System.out.println(loginIDPass);
        UserAuth userAuth = userAuthRepository.findByUsername(loginIDPass.getUsername());
        Map<String, Object> response = new HashMap<>();
        if(userAuth == null){
            response.put("success",false);
            response.put("message","Username does not match");
        }
        else{
            if(userAuth.getPassword().equals(loginIDPass.getPassword())){
                response.put("success" , true);
                response.put("message" , "Login successfully");
                response.put("userAuthData" , userAuth );
            }else{
                response.put("success", false);
                response.put("message" , "Password does not match");
            }
        }
        return ResponseEntity.ok(response);
    }
}
