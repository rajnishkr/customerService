package com.target.model;

import java.util.ArrayList;
import java.util.List;


public class CallHandler {

    private int numberOfJE;
    private int numberOfSE;
    private int numberOfManager;
    private int totalCalls;
    private int MAX_CALL_PER_EMPLOYEE;
    private List<List<Employee>> employeeLevels;
    private List<List<Call>> callQueues;
    private static CallHandler callHandler = null;

    private CallHandler() {

    }


    public static synchronized CallHandler getInstance(int numberOfJE, int numberOfSE, int numberOfManager, int totalCalls) {
        if (callHandler == null) {
            callHandler = new CallHandler();
            callHandler.numberOfJE = numberOfJE;
            callHandler.numberOfSE = numberOfSE;
            callHandler.numberOfManager = numberOfManager;
            callHandler.totalCalls = totalCalls;
            callHandler.MAX_CALL_PER_EMPLOYEE = totalCalls / (numberOfJE + numberOfSE);
            callHandler.employeeLevels = new ArrayList<List<Employee>>(3);
            callHandler.callQueues = new ArrayList<List<Call>>(3);

            ArrayList<Employee> jEs = new ArrayList<Employee>(numberOfJE);
            for (int k = 0; k < numberOfJE - 1; k++) {
                jEs.add(new JE(callHandler));
            }
            callHandler.employeeLevels.add(jEs);

            ArrayList<Employee> sEs = new ArrayList<Employee>(numberOfSE);
            sEs.add(new SE(callHandler));
            callHandler.employeeLevels.add(sEs);

            ArrayList<Employee> managers = new ArrayList<Employee>(numberOfManager);
            managers.add(new Manager(callHandler));
            callHandler.employeeLevels.add(managers);

        }
        return callHandler;
    }


    public Employee getHandlerForCall(Call call) {
        for (int level = call.getRank().getValue(); level < 3 - 1; level++) {
            List<Employee> employeeLevel = employeeLevels.get(level);
            for (Employee emp : employeeLevel) {
                if (emp.isFree()) {
                    return emp;
                }
            }
        }
        return null;
    }

    public void dispatchCall(Customer caller) {
        Call call = new Call(caller);
        dispatchCall(call);
    }

    public void dispatchCall(Call call) {
        Employee emp = getHandlerForCall(call);
        if (emp != null) {
            emp.receiveCall(call);
            call.setHandler(emp);
        } else {
            call.reply("Please wait for free JE employee to reply");
            callQueues.get(call.getRank().getValue()).add(call);
        }
    }

    public boolean assignCall(Employee emp) {
        for (int rank = emp.getRank().getValue(); rank >= 0; rank--) {
            List<Call> que = callQueues.get(rank);

            if (que.size() > 0) {
                Call call = que.remove(0);
                if (call != null) {
                    emp.receiveCall(call);
                    return true;
                }
            }
        }
        return false;
    }
}
