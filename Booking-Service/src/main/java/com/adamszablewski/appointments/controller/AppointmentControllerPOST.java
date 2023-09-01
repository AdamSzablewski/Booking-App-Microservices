package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments")
public class AppointmentControllerPOST {

    AppointmentService appointmentService;


}
