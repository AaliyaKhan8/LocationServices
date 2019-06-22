package com.example.aaliyakhan.locationservices;

/**
 * Created by AaliyaKhan on 27-05-2019.
 */

public class OrderData {
   private String name,from,to,code;

    public String getName() {
        return name;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }

    public String getCode() {
        return code;
    }

    public OrderData(String name,String from, String to,String code) {
        this.name = name;
        this.from=from;
        this.to = to;
        this.code=code;
    }

}
