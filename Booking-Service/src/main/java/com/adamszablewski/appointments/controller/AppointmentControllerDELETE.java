package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/appointments/cancel")
public class AppointmentControllerDELETE {
    AppointmentService appointmentService;

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAppointmentById(@RequestParam Long id){
        return appointmentService.deleteAppointmentById(id);
    }
    @DeleteMapping("/email/{email}")
    public ResponseEntity<String> deleteAppointmentByEmail(@RequestParam String email){
        return appointmentService.deleteAppointmentByEmail(email);
    }
    @DeleteMapping("/number/{number}")
    public ResponseEntity<String> deleteAppointmentByNumber(@RequestParam String number){
        return appointmentService.deleteAppointmentByNumber(number);
    }


}
