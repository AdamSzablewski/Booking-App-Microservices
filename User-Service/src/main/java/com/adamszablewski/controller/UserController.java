package com.adamszablewski.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.dto.UserClassDTO;

import com.adamszablewski.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<RestResponseDTO<UserClassDTO>> getUserById(@PathVariable long id){
        RestResponseDTO<UserClassDTO> responseDTO = RestResponseDTO.<UserClassDTO>builder()
                .value(userService.getUserById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/email")
    public ResponseEntity<RestResponseDTO<UserClassDTO>> getUserByEmail(@RequestHeader("userEmail") String userEmail){
        RestResponseDTO<UserClassDTO> responseDTO = RestResponseDTO.<UserClassDTO>builder()
                .value(userService.getUserByEMail(userEmail))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/")
    public ResponseEntity<RestResponseDTO<String>> deleteUser(@RequestHeader("userEmail") String userEmail){
        userService.deleteUser(userEmail);
        RestResponseDTO<String> responseDTO = RestResponseDTO.<String>builder()
                .value("User deleted")
                .build();
        return ResponseEntity.ok(responseDTO);
    }

}
