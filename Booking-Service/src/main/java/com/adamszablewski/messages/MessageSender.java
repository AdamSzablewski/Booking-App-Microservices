package com.adamszablewski.messages;

import com.adamszablewski.appointments.Appointment;
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
    public void createAppoinmentCanceledMessage(Appointment appointment) {
        Message message = Message.builder()
                .message("Your appoinment for the date "+appointment.getDate()+" has been canceled")
                .sender(APP_NAME)
                .receivers(List.of(appointment.getClient(), appointment.getEmployee()))
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
                .receivers(List.of(appointment.getEmployee(), appointment.getClient()))
                .dateSent(LocalDateTime.now())
                .build();

        rabbitMqProducer.sendMessageObject(message);

    }
}
