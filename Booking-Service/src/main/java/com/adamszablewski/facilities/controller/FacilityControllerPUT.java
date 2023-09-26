package com.adamszablewski.facilities.controller;

import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.service.FacilityService;
import lombok.AllArgsConstructor;
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
public class FacilityControllerPUT {

    private final FacilityService facilityService;
    //todo 1


}
