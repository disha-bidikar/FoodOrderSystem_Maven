package com.foodapp.models;

import java.time.LocalDate;

public class Employee {
    private int empId;
    private String name;
    private String phone;
    private String address;
    private String department;
    private LocalDate dob;
    private LocalDate joiningDate;

    public Employee(int empId, String name, String phone, String address, String department, LocalDate dob, LocalDate joiningDate) {
        this.empId = empId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.department = department;
        this.dob = dob;
        this.joiningDate = joiningDate;
    }

    public Employee(String name, String phone, String address, String department, LocalDate dob, LocalDate joiningDate) {
        this(0, name, phone, address, department, dob, joiningDate);
    }

    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getDepartment() { return department; }
    public LocalDate getDob() { return dob; }
    public LocalDate getJoiningDate() { return joiningDate; }

    @Override
    public String toString() {
        return empId + " | " + name + " | " + phone + " | " + address + " | " + department + " | DOB: " + dob + " | Joined: " + joiningDate;
    }
}
