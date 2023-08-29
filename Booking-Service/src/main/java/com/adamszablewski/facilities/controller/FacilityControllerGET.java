package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities")
public class FacilityControllerGET {

    private final FacilityService facilityService;

    @GetMapping("/")
    @ResponseBody
    public List<Facility> getAllFacilities(){
        return facilityService.getAllFacilities();
    }
    @GetMapping("/region/{region}")
    public List<Facility> getAllFacilitiesForRegion(@RequestParam String region){
        return facilityService.getAllFacilitiesForRegion(region);
    }
    @GetMapping("/id/{id}")
    public Optional<Facility> getFacilityById(@RequestParam Long id){
        return facilityService.getFacilityById(id);
    }
    @GetMapping("/city/{city}")
    public List<Facility> getAllFacilitiesForCity(@RequestParam String city){
        return facilityService.getAllFacilitiesForCity(city);
    }
}
