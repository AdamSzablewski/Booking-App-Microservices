package com.adamszablewski.helpers;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.model.Facility;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public boolean isOwner(Facility facility, String userEmail){
        return facility.getOwner().getUser().getEmail().equals(userEmail);
    }
    public boolean isEmployee(Facility facility, String userEmail){
        return facility.getEmployees().stream()
                .anyMatch(employee -> employee.getUser().getEmail().equals(userEmail));
    }
    public boolean isClient(Appointment appointment, String userEmail){
        return appointment.getClient().getUser().getEmail().equals(userEmail);
    }
    public boolean isOwnerEmployeeOrTheClient(Appointment appointment, String usereEmail){
        return isClient(appointment, usereEmail) || isEmployee(appointment.getFacility(), usereEmail)
                || isOwner(appointment.getFacility(), usereEmail);

    }
}
