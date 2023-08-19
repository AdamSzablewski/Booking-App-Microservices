package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import com.adamszablewski.services.Service;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/facilities/change")
public class FacilityControllerPATCH {

    private final FacilityService facilityService;

    @PatchMapping("id/{id}/add/services/")
    public ResponseEntity<String> addServiceToFacility(@RequestParam BigInteger id, @RequestBody Service service){
        return facilityService.addServiceToFacility(id, service);
    }

}
