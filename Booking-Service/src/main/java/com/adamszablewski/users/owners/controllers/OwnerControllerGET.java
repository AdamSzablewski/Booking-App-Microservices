package com.adamszablewski.users.owners.controllers;

import com.adamszablewski.users.owners.Owner;
import com.adamszablewski.users.owners.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/owners")
@Controller
@AllArgsConstructor
public class OwnerControllerGET {

    OwnerService ownerService;

    @GetMapping("/email/{email}")
    public Owner getOwnerByEmail(@RequestParam String email){
        return ownerService.getOwnerByEmail(email);
    }

}
