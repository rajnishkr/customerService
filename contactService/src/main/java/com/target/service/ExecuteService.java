package com.target.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.handler.CallHandler;
import com.target.model.Customer;
import com.target.model.Employee;
import com.target.request.Request;
import com.target.response.EResponse;
import com.target.response.Performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class ExecuteService {

    public static void main(String[] args) {
        Request request = getDummyRequestObject();
        String jEsCalls[] = request.getJe().get(0).split(",");
        String sEsCalls[] = request.getSe().get(0).split(",");
        int numberOfJE = jEsCalls.length;
        int numberOfSE = sEsCalls.length;
        int numberOfManager = 0;
        CallHandler callHandler = CallHandler.getInstance(numberOfJE, numberOfSE, numberOfManager, request.getNumber_of_calls());
        for (int i = 0; i < request.getJe().size(); i++) {
            String calls[] = request.getJe().get(i).split(",");
            for (String jE : calls) {
                callHandler.dispatchCall(getDummyCustomer(), Integer.parseInt(jE));
            }
        }
        displayResponse(callHandler.getEmployeeLevels());

        System.out.println("");
    }

    private static void displayResponse(List<PriorityQueue<Employee>> employeeLevels) {
        List<EResponse> junior_executives = new ArrayList<>();
        List<EResponse> senior_executives = new ArrayList<>();
        List<EResponse> manager = new ArrayList<>();
        employeeLevels.get(0).stream().forEach(x -> {
            int resol = x.getNumberOfCallReceived() - x.getCallEscalated();
            junior_executives.add(new EResponse(x.getId() + "", x.getTotalTimeSpent() + "", x.getNumberOfCallReceived() + "", resol + "", x.getCallEscalated() + ""));
        });
        employeeLevels.get(1).stream().forEach(x -> {
            int resol = x.getNumberOfCallReceived() - x.getCallEscalated();
            senior_executives.add(new EResponse(x.getId() + "", x.getTotalTimeSpent() + "", x.getNumberOfCallReceived() + "", resol + "", x.getCallEscalated() + ""));
        });

        employeeLevels.get(2).stream().forEach(x -> {
            int resol = x.getNumberOfCallReceived() - x.getCallEscalated();
            manager.add(new EResponse(x.getId() + "", x.getTotalTimeSpent() + "", x.getNumberOfCallReceived() + "", resol + "", x.getCallEscalated() + ""));
        });
        Performance performance = new Performance(manager.get(0), junior_executives, senior_executives);
        try {
            System.out.println("new ObjectMapper() = " + new ObjectMapper().writeValueAsString(performance));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static Customer getDummyCustomer() {
        return new Customer();
    }

    public static Request getDummyRequestObject() {

        List<String> je = Arrays.asList("5,7,6,4,6",
                "5,8,7,5,10",
                "7,5,6,14,6",
                "10,4,9,5,12",
                "6,10,11,4,6");

        List<String> se = Arrays.asList("6,14,12,10,5",
                "18,8,6,4,12",
                "8,6,13,7,1");
        String mgr = "20,12,25,13,20,3,3,3,9,2,7,1,7,11,10";

        Request request = new Request(30, je, se, mgr);
        return request;

    }
}
