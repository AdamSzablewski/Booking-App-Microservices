package com.adamszablewski.feignClients;


import com.adamszablewski.dto.RestResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MESSAGING")
public interface MessagingServiceClient {

    @DeleteMapping("/conversations/delete/user/{id}")
    RestResponseDTO<String> deleteConversation(@PathVariable long id);

}
