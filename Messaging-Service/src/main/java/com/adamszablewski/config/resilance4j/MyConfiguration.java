//package com.adamszablewski.config.resilance4j;
//
//import org.springframework.boot.actuate.health.Status;
//import org.springframework.boot.actuate.health.StatusAggregator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Set;
//
//@Configuration
//public class MyConfiguration {
//
//    @Bean
//    public StatusAggregator statusAggregator() {
//
//        return new StatusAggregator() {
//            @Override
//            public Status getAggregateStatus(Status... statuses) {
//                return StatusAggregator.super.getAggregateStatus(statuses);
//            }
//
//            @Override
//            public Status getAggregateStatus(Set<Status> statuses) {
//                return null;
//            }
//        };
//    }
//
//    // Other configuration beans or methods if needed
//}
