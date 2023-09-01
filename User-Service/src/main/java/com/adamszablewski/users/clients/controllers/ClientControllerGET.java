package com.adamszablewski.users.clients.controllers;

import com.adamszablewski.users.clients.Client;
import com.adamszablewski.users.clients.service.ClientService;
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
@RequestMapping("/users/clients")
public class ClientControllerGET {

    ClientService clientService;

    @GetMapping("/email/{email}")
    @ResponseBody
    public Client getClientByMail(@PathVariable String email){
        return clientService.getClientByMail(email);
    }
    @GetMapping("/id/{id}")
    @ResponseBody
    public Client getClientById(@PathVariable long id){
        return clientService.getClientById(id);
    }

}
