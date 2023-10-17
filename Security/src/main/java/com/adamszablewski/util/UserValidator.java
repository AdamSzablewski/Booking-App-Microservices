package com.adamszablewski.util;


import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.exception.MissingFeignValueException;
import com.adamszablewski.feign.BookingServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class UserValidator {
    private final BookingServiceClient bookingServiceClient;
    public boolean isOwner(long facilityId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getOwnerMailById(facilityId);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        String ownerEmail = response.getValue();
        return ownerEmail.equals(userEmail);
    }

    public boolean isUser(long userId, String email){
        return true;
    }
    public boolean isEmployee(long facilityId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getEmployeesForFacility(facilityId);
        if (response.getValues() == null){
            throw new MissingFeignValueException();
        }
        Set<String> employeeMails = response.getValues();
        return employeeMails.stream()
                .anyMatch(mail -> mail.equals(userEmail));
    }
    public boolean isClient(long appointmentId, String userEmail){
        RestResponseDTO<String> response =  bookingServiceClient.getClientForAppointment(appointmentId);
        if (response.getValue() == null){
            throw new MissingFeignValueException();
        }
        String clientEmail = response.getValue();
        return clientEmail.equals(userEmail);
    }

    public boolean isOwnerOrEmployeeOrTheClient(long appointmentId, String usereEmail){
        return isClient(appointmentId, usereEmail) || isEmployee(appointmentId, usereEmail)
                || isOwner(appointmentId, usereEmail);
        //todo

    }
    public boolean isOwnerOrEmployee(long appointmentId, String userEmail){
        return isOwner(appointmentId, userEmail) || isEmployee(appointmentId, userEmail);
        //todo
    }
}
