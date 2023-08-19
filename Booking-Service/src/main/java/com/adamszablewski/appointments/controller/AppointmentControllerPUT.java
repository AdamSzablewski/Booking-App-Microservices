package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigInteger;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/change")
public class AppointmentControllerPUT {
    AppointmentService appointmentService;

    @PutMapping("/id/{id}/employee/email/{email}")
    public ResponseEntity<String> changeEmployeeForAppointmentById(@RequestParam BigInteger id,
                                                                   @RequestParam String email){
        return appointmentService.changeEmployeeForAppointmentById(id, email);
    }
}
