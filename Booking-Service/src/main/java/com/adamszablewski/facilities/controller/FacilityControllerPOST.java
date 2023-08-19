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
@RequestMapping("/facilities/create")
public class FacilityControllerPOST {

    private final FacilityService facilityService;

    @PostMapping("/")
    ResponseEntity<String> createFacility(@RequestBody Facility facility){
      return facilityService.createFacility(facility);
  }
}
