package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.service.FacilityService;
import com.adamszablewski.tasks.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities/change")
public class FacilityControllerPATCH {

    private final FacilityService facilityService;

    @PatchMapping("id/{id}/add/services/")
    public ResponseEntity<String> addServiceToFacility(@RequestParam Long id, @RequestBody Task service){
        return facilityService.addServiceToFacility(id, service);
    }

}
