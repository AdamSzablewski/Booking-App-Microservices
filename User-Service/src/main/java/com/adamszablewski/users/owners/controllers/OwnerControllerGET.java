package com.adamszablewski.users.owners.controllers;

import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.owners.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@AllArgsConstructor
@RequestMapping("/users/owners")
public class OwnerControllerGET {

    OwnerService ownerService;

    @GetMapping("/email/{email}")
    @ResponseBody
    public Owner getOwnerByEmail(@PathVariable String email){
        System.out.println("called");
        return ownerService.getOwnerByEmail(email);
    }

}
