package com.adamszablewski.util;


import org.apache.logging.log4j.core.net.Facility;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public boolean isOwner(long facilityId, String userEmail){
        //return facility.getOwner().getUser().getEmail().equals(userEmail);
        return true;
    }
    public boolean isUser(long user, String email){
        return true;
    }
    public boolean isEmployee(Facility facility, String userEmail){
      return true;
    }


}
