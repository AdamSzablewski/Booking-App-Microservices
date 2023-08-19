package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerGET {

    private final FacilityService facilityService;

    @GetMapping("/")
    List<Facility> getAllFacilities(){
        return facilityService.getAllFacilities();
    }
    @GetMapping("/region/{region}")
    List<Facility> getAllFacilitiesForRegion(@RequestParam String region){
        return facilityService.getAllFacilitiesForRegion(region);
    }
    @GetMapping("/id/{id}")
    Optional<Facility> getFacilityById(@RequestParam BigInteger id){
        return facilityService.getFacilityById(id);
    }
    @GetMapping("/city/{city}")
    List<Facility> getAllFacilitiesForCity(@RequestParam String city){
        return facilityService.getAllFacilitiesForCity(city);
    }
}
