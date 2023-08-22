package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerDELETE {

    private final FacilityService facilityService;

    @DeleteMapping("/id/{id}")
    ResponseEntity<String> removeFacilityById(@RequestParam Long id){
        return facilityService.removeFacilityById(id);
    }

}
