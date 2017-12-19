package com.target.model;

import com.target.handler.CallHandler;
import com.target.model.entity.EmployeeRank;

public class SE extends Employee {
    public SE(CallHandler callHandler, int escalationThreshold, int id) {
        super(callHandler, escalationThreshold, id);
        rank = EmployeeRank.SE;
    }

}
