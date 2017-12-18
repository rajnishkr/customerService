package com.target.model;

import com.target.handler.CallHandler;
import com.target.model.entity.EmployeeRank;

public class SE extends Employee {
    public SE(CallHandler callHandler, int escalationThreshold) {
        super(callHandler, escalationThreshold);
        rank = EmployeeRank.SE;
    }

}
