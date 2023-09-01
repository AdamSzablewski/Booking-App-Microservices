package com.adamszablewski.users;

import com.adamszablewski.users.employee.Employee;
import com.adamszablewski.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    UserService userService;
    @PutMapping
    void saveAllUsers(@RequestBody List<UserClass> users){
        userService.saveAllUsers(users);
    }

}
