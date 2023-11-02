package com.adamszablewski.messages;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.util.helpers.UserTools;
import com.adamszablewski.model.Facility;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.rabbitMq.classes.MessageDto;
import com.adamszablewski.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.adamszablewski.Variables.APP_NAME;

@Component
@AllArgsConstructor
public class MessageSender {

    private final RabbitMqProducer rabbitMqProducer;
    private final UserServiceClient userServiceClient;
    private final UserTools userTools;
    public void createAppoinmentCanceledMessage(Appointment appointment, Long id) {
        MessageDto message = MessageDto.builder()
                .message("Your appoinment for the date "+appointment.getDate()+" has been canceled")
                .recipient(id)
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);
    }


    public void sendAppointmentCreatedMessage(Appointment appointment, long userId) {
        String city = appointment.getFacility().getCity();
        String street = appointment.getFacility().getStreet();
        String house = appointment.getFacility().getHouseNumber();

        MessageDto message = MessageDto.builder()
                .message("You have a new appoinment for: "+appointment.getTask().getName()+" with "
                        +appointment.getFacility().getName()+" on " +
                        appointment.getDate()+" at "+appointment.getStartTime()+
                        ". The address is: "+street+" "+house+" in "+city+". Your appoinment will cost "
                        +appointment.getTask().getPrice()+" "+appointment.getTask().getCurrency())
                .recipient(userId)
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);

    }

    public void createFacilityCreatedMessage(String ownerEmail, Facility facility) {
        UserClass user = userTools.getUserByEmail(ownerEmail);
        MessageDto message = MessageDto.builder()
                .message("Congratulations your new facility "+facility.getName()+" has been created. " +
                        "To start taking appointments create one or more services that you offer.")
                .recipient(user.getId())
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestMessage(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        MessageDto message = MessageDto.builder()
                .recipient(user.getId())
                .message("You have reieved a request to start working for: "+facility.getName()+" " +
                        "to accept request go to requests")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestDenied(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        UserClass owner = facility.getOwner().getUser();
        MessageDto message = MessageDto.builder()
                .recipient(owner.getId())
                .message("The user "+user.getEmail()+" hase denied your employment request")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestAccepted(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        UserClass owner = facility.getOwner().getUser();
        MessageDto message = MessageDto.builder()
                .recipient(owner.getId())
                .message("The user "+user.getEmail()+" has accepted your employment request")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendTaskCreatedMessage(Facility facility, Task task) {
        UserClass owner = facility.getOwner().getUser();
        MessageDto message = MessageDto.builder()
                .recipient(owner.getId())
                .message("You have successfully added the service "+task.getName()+" to your facility.")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendAppointmentDoneMessage(Appointment appointment) {
        UserClass user = appointment.getClient().getUser();
        MessageDto message = MessageDto.builder()
                .recipient(user.getId())
                .message("Your appointment has been completed")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }
}
