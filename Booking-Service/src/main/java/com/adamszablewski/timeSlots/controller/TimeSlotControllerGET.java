package com.adamszablewski.timeSlots.controller;

import com.adamszablewski.timeSlots.TimeSlot;
import com.adamszablewski.timeSlots.service.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/timeslots")
public class TimeSlotControllerGET {

    TimeSlotService timeSlotService;

    @GetMapping("/task/id/{id}")
    @ResponseBody
    public List<TimeSlot> getAvailableTimeSlotsForTaskAndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                  LocalDate date,
                                                              @PathVariable Long id){
        return timeSlotService.getAvailableTimeSlotsForTaskAndDate(date, id);
    }
}
