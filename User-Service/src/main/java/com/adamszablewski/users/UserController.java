package com.adamszablewski.users;

import com.adamszablewski.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    UserService userService;
    @PutMapping
    void saveAllUsers(@RequestBody List<UserClass> users){
        userService.saveAllUsers(users);
    }
}
