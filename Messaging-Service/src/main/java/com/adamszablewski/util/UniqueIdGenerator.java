package com.adamszablewski.util;

import java.util.UUID;

public class UniqueIdGenerator {
    public String generateUniqueId() {

        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        return uuid.toString() + "-" + timestamp;
    }


    public String generateUniqueImageId() {
        UUID uuid = UUID.randomUUID();
        long timestamp = System.currentTimeMillis();
        return "IMAGE"+uuid.toString() + "-" + timestamp;
    }
}