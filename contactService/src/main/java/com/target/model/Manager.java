package com.target.model;

import com.target.handler.CallHandler;
import com.target.model.entity.EmployeeRank;

public class Manager extends Employee {

    public Manager(CallHandler callHandler, int escalationThreshold) {
        super(callHandler, escalationThreshold);
        rank = EmployeeRank.MANAGER;
    }

}
