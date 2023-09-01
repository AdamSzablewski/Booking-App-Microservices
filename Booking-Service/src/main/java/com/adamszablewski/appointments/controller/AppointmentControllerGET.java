package com.adamszablewski.appointments.controller;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.service.AppointmentService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/appointments")
public class AppointmentControllerGET {
    AppointmentService appointmentService;

    @GetMapping("/")
    @ResponseBody
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }
    @GetMapping("/id/{id}")
    public Optional<Appointment> getAppointmentById(@RequestParam Long id){
        return appointmentService.getAppointmentById(id);
    }
    @GetMapping("/number/{number}")
    public Optional<Appointment> getAppointmentByPhoneNumber(@RequestParam String number){
        return appointmentService.getAppointmentByPhoneNumber(number);
    }
    @GetMapping("/email/{email}")
    public Optional<Appointment> getAppointmentByEmail(@RequestParam String email){
        return appointmentService.getAppointmentByEmail(email);
    }

}
