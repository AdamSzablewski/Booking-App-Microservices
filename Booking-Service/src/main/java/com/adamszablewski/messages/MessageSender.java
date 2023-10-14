package com.adamszablewski.messages;

import com.adamszablewski.model.Appointment;
import com.adamszablewski.util.helpers.UserTools;
import com.adamszablewski.model.Facility;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.model.Employee;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.rabbitMq.classes.Message;
import com.adamszablewski.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.adamszablewski.Variables.APP_NAME;

@Component
@AllArgsConstructor
public class MessageSender {

    private final RabbitMqProducer rabbitMqProducer;
    private final UserServiceClient userServiceClient;
    private final UserTools userTools;
    public void createAppoinmentCanceledMessage(Appointment appointment) {
        Message message = Message.builder()
                .message("Your appoinment for the date "+appointment.getDate()+" has been canceled")
                .sender(APP_NAME)
                .receivers(List.of(appointment.getClient().getUser().getId(), appointment.getEmployee().getUser().getId()))
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendAppointmentCreatedMessage(Appointment appointment) {
        String city = appointment.getFacility().getCity();
        String street = appointment.getFacility().getStreet();
        String house = appointment.getFacility().getHouseNumber();

        Message message = Message.builder()
                .message("You have a new appoinment for: "+appointment.getTask().getName()+" with "
                        +appointment.getFacility().getName()+" on " +
                        appointment.getDate()+" at "+appointment.getStartTime()+
                        ". The address is: "+street+" "+house+" in "+city+". Your appoinment will cost "
                        +appointment.getTask().getPrice()+" "+appointment.getTask().getCurrency())
                .sender(APP_NAME)
                .receivers(List.of(appointment.getEmployee().getUser().getId(), appointment.getClient().getUser().getId()))
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);

    }
    public void sendAppointmentCreatedMessage(Appointment appointment, long userId) {
        String city = appointment.getFacility().getCity();
        String street = appointment.getFacility().getStreet();
        String house = appointment.getFacility().getHouseNumber();

        Message message = Message.builder()
                .message("You have a new appoinment for: "+appointment.getTask().getName()+" with "
                        +appointment.getFacility().getName()+" on " +
                        appointment.getDate()+" at "+appointment.getStartTime()+
                        ". The address is: "+street+" "+house+" in "+city+". Your appoinment will cost "
                        +appointment.getTask().getPrice()+" "+appointment.getTask().getCurrency())
                .sender(APP_NAME)
                .receivers(List.of(userId))
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);

    }

    public void createFacilityCreatedMessage(String ownerEmail, Facility facility) {
        UserClass user = userTools.getUserByEmail(ownerEmail);
        Message message = Message.builder()
                .message("Congratulations your new facility "+facility.getName()+" has been created. " +
                        "To start taking appointments create one or more services that you offer.")
                .receivers(List.of(user.getId()))
                .sender(APP_NAME)
                .dateSent(LocalDateTime.now())
                .build();
        //System.out.println(message);
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestMessage(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        System.out.println(employee);
        System.out.println(user);
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(user.getId()))
                .message("You have reieved a request to start working for: "+facility.getName()+" " +
                        "to accept request go to requests")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestDenied(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        UserClass owner = facility.getOwner().getUser();
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(owner.getId()))
                .message("The user "+user.getEmail()+" hase denied your employment request")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestAccepted(Employee employee, Facility facility) {
        UserClass user = employee.getUser();
        UserClass owner = facility.getOwner().getUser();
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(owner.getId()))
                .message("The user "+user.getEmail()+" hase accepted your employment request")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendTaskCreatedMessage(Facility facility, Task task) {
        UserClass owner = facility.getOwner().getUser();
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(owner.getId()))
                .message("You have successfully added the service "+task.getName()+" to your facility.")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendAppointmentDoneMessage(Appointment appointment) {
        UserClass user = appointment.getClient().getUser();
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(user.getId()))
                .message("Your appointment has been completed")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }
}
