package com.adamszablewski.messages.rabbitMqConsumer;


import com.adamszablewski.messages.Message;
import com.adamszablewski.messages.service.ConversationService;
import com.adamszablewski.messages.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static com.adamszablewski.messages.rabbitMqConsumer.RabitMqVariables.QUEUE;

@Service
@AllArgsConstructor
public class RabbitMqConsumer {

    MessageService messageService;



//    @RabbitListener(queues = RabbitMqConfig.SALES_ORDER_CONFIRMATION_QUEUE)
//    public void consume(SalesOrderConfirmation salesOrderConfirmation){
//        System.out.println("||| Message recieved |||||| "+ salesOrderConfirmation.toString());
//    }
    @RabbitListener(queues = RabbitMqConfig.MESSAGE_MEASSAGE_QUEUE)
    public void consume(Message message){
        messageService.addMessageToConversation(message);
        System.out.println("||| Message Object recieved |||||| "+ message.toString());
    }
}
