package com.target.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;

@Setter
@Getter
@Data
public class Employee {

    private int id;
    private String name;
    EmployeeRank rank;
    private Employee boss;
    private CallHandler callHandler;
    private Call currentCall;
    private EmployeeStatus employeeStatus;

    public Employee(CallHandler handler) {
        Random random = new Random();
        id = random.nextInt();
        callHandler = handler;
    }

    public void receiveCall(Call call) {
        this.employeeStatus = EmployeeStatus.OnCall;
        currentCall = call;
    }


    public void callCompleted() {
        if (currentCall != null) {
            currentCall.endCall();
            currentCall = null;
            employeeStatus = EmployeeStatus.Available;
        }

        assignNewCall();
    }

    public void escalateAndReassign() {
        if (currentCall != null) {
            currentCall.incrementRank();
            callHandler.dispatchCall(currentCall);
            currentCall = null;
            employeeStatus = EmployeeStatus.Available;
        }

        assignNewCall();
    }

    public boolean assignNewCall() {
        if (!isFree()) {
            return false;
        }
        return callHandler.assignCall(this);
    }


    public boolean isFree() {
        return (employeeStatus == EmployeeStatus.Available) && currentCall == null;
    }

    public EmployeeRank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId() &&
                Objects.equals(getName(), employee.getName()) &&
                getRank() == employee.getRank();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), getRank());
    }
}
