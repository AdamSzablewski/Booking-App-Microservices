package com.adamszablewski.users;

import com.adamszablewski.dto.UserClassDTO;
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

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<RestResponseDTO<UserClassDTO>> getUserById(@PathVariable long id){
        RestResponseDTO<UserClassDTO> responseDTO = RestResponseDTO.<UserClassDTO>builder()
                .value(userService.getUserById(id))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<RestResponseDTO<String>> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        RestResponseDTO<String> responseDTO = RestResponseDTO.<String>builder()
                .value("User deleted")
                .build();
        return ResponseEntity.ok(responseDTO);
    }

}
