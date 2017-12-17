package com.target.model;

public class SE extends Employee {
    private int numberOfcallRecieved;
    private int totalTimeSpent;
    private int callEscalated;
    public SE(CallHandler callHandler) {
        super(callHandler);
        this.numberOfcallRecieved = 0;
        this.totalTimeSpent = 0;
        this.callEscalated = 0;
        rank = EmployeeRank.SE;
    }

}
