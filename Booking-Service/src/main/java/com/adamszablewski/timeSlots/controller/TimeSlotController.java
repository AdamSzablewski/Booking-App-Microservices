package com.adamszablewski.timeSlots.controller;

import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotController {

    TimeSlotService timeSlotService;

    @GetMapping("/task/id/{id}")
    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(@RequestBody LocalDate date, @RequestParam Long id){
        return timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, id);
    }
}
