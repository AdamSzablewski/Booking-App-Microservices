package com.adamszablewski.messages;

import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Employee;
import com.adamszablewski.feignClients.classes.UserClass;
import com.adamszablewski.rabbitMq.RabbitMqProducer;
import com.adamszablewski.rabbitMq.classes.Message;
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
    public void createAppoinmentCanceledMessage(Appointment appointment) {
        Message message = Message.builder()
                .message("Your appoinment for the date "+appointment.getDate()+" has been canceled")
                .sender(APP_NAME)
                .receivers(List.of(appointment.getClient().getUserClass(), appointment.getEmployee().getUserClass()))
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
                .receivers(List.of(appointment.getEmployee().getUserClass(), appointment.getClient().getUserClass()))
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);

    }

    public void createFacilityCreatedMessage(String ownerEmail, Facility facility) {
        UserClass user = userServiceClient.findUserByEmail(ownerEmail).getValue();
        Message message = Message.builder()
                .message("Congratulations your new facility "+facility.getName()+" has been created. " +
                        "To start taking appointments create one or more services that you offer.")
                .receivers(List.of(user))
                .sender(APP_NAME)
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }

    public void sendEmploymentRequestMessage(Employee employee, Facility facility) {
        UserClass user = employee.getUserClass();
        Message message = Message.builder()
                .sender(APP_NAME)
                .receivers(List.of(user))
                .message("You have reieved a request to start working for: "+facility.getName()+" " +
                        "to accept request go to requests")
                .dateSent(LocalDateTime.now())
                .build();
        rabbitMqProducer.sendMessageObject(message);
    }
}
