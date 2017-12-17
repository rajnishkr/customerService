package com.target.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Customer {
    private String firstName;
    private String secondName;
    private String registeredMobileNumber;
    private CustomerGrade customerGrade;
    private ServiceType serviceType;
}
