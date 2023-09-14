package com.adamszablewski.users;

import com.adamszablewski.dtos.RestResponseDTO;

import com.adamszablewski.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    UserService userService;

//    @PostMapping("/authenticate")
//    public ResponseEntity<RestResponseDTO<String>> authenticate(@RequestBody AuthRequest authRequest){
//        jwtService.generateToken(authRequest.getEmail())
//    }
    @PutMapping
    void saveAllUsers(@RequestBody List<UserClass> users){
        userService.saveAllUsers(users);
    }

}
