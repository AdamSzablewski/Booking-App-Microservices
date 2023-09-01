package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/change")
public class AppointmentControllerPUT {
    AppointmentService appointmentService;

    @PutMapping("/id/{id}/employee/email/{email}")
    public ResponseEntity<String> changeEmployeeForAppointmentById(@PathVariable Long id){
        return appointmentService.changeEmployeeForAppointmentById(id);
    }
}
