package com.adamszablewski.rabbitMq;


import com.adamszablewski.appointments.Appointment;
import com.adamszablewski.rabbitMq.classes.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.adamszablewski.rabbitMq.RabbitMqConfig.*;

import static com.adamszablewski.rabbitMq.RabbitMqConfig.EXCHANGE_NAME;


@Service
public class RabbitMqProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Appointment appointment){
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "alley", appointment);
    }


    public void sendMessageObject(Message message) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "mqkey", message);
    }
}
