package com.adamszablewski.exceptions;

public class NoSuchConversationFound extends RuntimeException{
    public NoSuchConversationFound(){}
    public NoSuchConversationFound(String message){
        super(message);
    }
}
