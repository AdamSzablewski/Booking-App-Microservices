package com.adamszablewski.timeSlots.controller;

import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotControllerPOST {

    TimeSlotService timeSlotService;

    @PostMapping("/user/id/{id}")
    public ResponseEntity<String> makeAppointmentFromTimeSlot(@RequestParam long id, @RequestBody TimeSlot timeSlot){
        timeSlotService.makeAppointmentFromTimeSlot(id, timeSlot);
    }

}
