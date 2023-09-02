package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/cancel")
public class AppointmentControllerDELETE {
    AppointmentService appointmentService;

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id){
        return appointmentService.deleteAppointmentById(id);
    }
    @DeleteMapping("/email/{email}")
    public ResponseEntity<String> deleteAppointmentByEmail(@PathVariable String email){
        return appointmentService.deleteAppointmentByEmail(email);
    }
    @DeleteMapping("/number/{number}")
    public ResponseEntity<String> deleteAppointmentByNumber(@PathVariable String number){
        return appointmentService.deleteAppointmentByNumber(number);
    }


}
