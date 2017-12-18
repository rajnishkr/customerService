package com.target.model;

import com.target.handler.CallHandler;
import com.target.model.entity.EmployeeRank;

public class JE extends Employee {
    public JE(CallHandler callHandler, int escalationThreshold) {

        super(callHandler, escalationThreshold);
        rank = EmployeeRank.JE;
    }

}
