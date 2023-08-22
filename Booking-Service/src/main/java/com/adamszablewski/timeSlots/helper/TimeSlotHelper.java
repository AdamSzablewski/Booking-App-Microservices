package com.adamszablewski.timeSlots.helper;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.appointments.repository.AppointmentRepository;
import com.adamszablewski.users.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
@Component
@AllArgsConstructor
public class TimeSlotHelper {

    AppointmentRepository appointmentRepository;
    public boolean isTimeSlotAvailable(LocalTime startTime, LocalTime endTime, Employee employee, LocalDate date) {
        List<Appointment> existingAppointments = appointmentRepository.findByEmployeeAndDate(employee, date);
        AtomicBoolean isAvailable = new AtomicBoolean(true);
        existingAppointments.forEach(appointment -> {
            if (doTimeRangesOverlap(appointment.getStartTime(), appointment.getEndTime(), startTime, endTime)) {
                isAvailable.set(false);

            }
            if (!isWithinEmployeeWorkHours(startTime, endTime, employee)) {
                isAvailable.set(false);
            }

        });
        return isAvailable.get();
    }
    public boolean doTimeRangesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !end1.isBefore(start2) && !start1.isAfter(end2);
    }
    public boolean isWithinEmployeeWorkHours(LocalTime startTime, LocalTime endTime, Employee employee) {
        LocalTime employeeStartTime = employee.getStartTime();
        LocalTime employeeEndTime = employee.getEndTime();

        return !startTime.isBefore(employeeStartTime) && !endTime.isAfter(employeeEndTime);
    }

}
