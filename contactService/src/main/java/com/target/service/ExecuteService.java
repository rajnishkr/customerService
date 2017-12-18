package com.target.service;

import com.target.handler.CallHandler;
import com.target.request.Request;

import java.util.Arrays;
import java.util.List;

public class ExecuterService {

    public static void main(String[] args) {
        CallHandler callHandler = CallHandler.getInstance();
    }

    public static Request getDummyRequestObject(){

        List<String> je = Arrays.asList( "5,7,6,4,6",
                "5,8,7,5,10",
                "7,5,6,14,6",
                "10,4,9,5,12",
                "6,10,11,4,6");

        List<String> se = Arrays.asList( "6,14,12,10,5",
                "18,8,6,4,12",
                "8,6,13,7,1");
        String mgr= 
    }
}
