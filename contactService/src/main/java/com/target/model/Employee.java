package com.target.model;

import com.target.handler.CallHandler;
import com.target.model.entity.EmployeeRank;
import com.target.model.entity.EmployeeStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;

@Setter
@Getter
@Data
public class Employee implements Comparable<Employee> {


    private final int escalationThreshold;
    EmployeeRank rank;
    private int id;
    private String name;
    private Employee boss;
    private CallHandler callHandler;
    private Call currentCall;
    private EmployeeStatus employeeStatus;
    private int numberOfCallReceived;
    private int totalTimeSpent;
    private int callEscalated;

    public Employee(CallHandler handler, int escalationThreshold) {
        this.numberOfCallReceived = 0;
        this.totalTimeSpent = 0;
        this.callEscalated = 0;
        this.escalationThreshold = escalationThreshold;
        Random random = new Random();
        id = random.nextInt(100);
        callHandler = handler;
        this.currentCall = null;
        this.employeeStatus = EmployeeStatus.Available;
    }

    public void receiveCall(Call call) {
        this.employeeStatus = EmployeeStatus.OnCall;
        currentCall = call;
        call.setHandler(this);
        currentCall.startCall();
        this.numberOfCallReceived++;
        this.totalTimeSpent += call.getCallDuration();
        if (call.getCallDuration() >= this.escalationThreshold) {
            this.callEscalated++;
            escalateAndReassign();
        }
    }


    public void callCompleted() {
        if (currentCall != null) {
            currentCall.endCall();
            currentCall = null;
            employeeStatus = EmployeeStatus.Available;
        }

    }

    public void escalateAndReassign() {
        if (currentCall != null && currentCall.getRank() != EmployeeRank.MANAGER) {
            currentCall.setRank(currentCall.incrementRank());
            System.out.println("\n\n call being escalated to  " + currentCall.getRank());
            callHandler.dispatchCall(currentCall);
            currentCall = null;
            employeeStatus = EmployeeStatus.Available;
        }

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

    public int compareTo(Employee e) {
        if (this.numberOfCallReceived == callHandler.getMAX_CALL_PER_EMPLOYEE()) {
            return -1;
        }
        if (this.employeeStatus != EmployeeStatus.Available)
            return -1;

        return this.totalTimeSpent - e.totalTimeSpent;
    }
}
