package com.target.model;

public class JE extends Employee {
    private int numberOfcallRecieved;
    private int totalTimeSpent;
    private int callEscalated;

    public JE(CallHandler callHandler) {

        super(callHandler);
        this.numberOfcallRecieved = 0;
        this.totalTimeSpent = 0;
        this.callEscalated = 0;
        rank = EmployeeRank.JE;
    }

}
