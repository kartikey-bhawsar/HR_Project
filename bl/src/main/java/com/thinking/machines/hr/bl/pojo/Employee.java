package com.thinking.machines.hr.bl.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.EmployeeInterface;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;

public class Employee implements Cloneable,EmployeeInterface{
    private String employeeId;
    private String name;
    private DesignationInterface designation;
    private Date dateOfBirth;
    private char gender;
    private boolean isIndian;
    private BigDecimal basicSalary;
    private String aadharNumber;
    private String panNumber;

    public Employee() {
        this.employeeId = "";
        this.name = "";
        this.designation=null;
        this.dateOfBirth = null;
        this.gender = ' ';
        this.isIndian = false;
        this.basicSalary = null;
        this.aadharNumber = "";
        this.panNumber = "";
    }

    public void setEmployeeId(java.lang.String employeeId) {
        this.employeeId = employeeId;
    }

    public java.lang.String getEmployeeId() {
        return this.employeeId;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setDesignation(DesignationInterface designation) {
        this.designation = designation;
    }

    public DesignationInterface getDesignation() {
        return this.designation;
    }

    public void setDateOfBirth(java.util.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public java.util.Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public char getGender() {
        return this.gender;
    }

    public void setIsIndian(boolean isIndian) {
        this.isIndian = isIndian;
    }

    public boolean isIndian() {
        return this.isIndian;
    }

    public void setBasicSalary(java.math.BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public java.math.BigDecimal getBasicSalary() {
        return this.basicSalary;
    }

    public void setAadharNumber(java.lang.String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public java.lang.String getAadharNumber() {
        return this.aadharNumber;
    }

    public void setPANNumber(java.lang.String panNumber) {
        this.panNumber = panNumber;
    }

    public java.lang.String getPANNumber() {
        return this.panNumber;
    }

    public boolean equals(Object other)
    {
        if(!(other instanceof Employee)) return false;
        Employee employee =(Employee)other;
        return this.employeeId.equalsIgnoreCase(employee.getEmployeeId());
    }

    public int compareTo(EmployeeInterface other)
    {
        return this.employeeId.compareToIgnoreCase(other.getEmployeeId());
    }

    public int hashCode()
    {
        return this.employeeId.toUpperCase().hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", name=" + name + ", designation=" + designation
                + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", isIndian=" + isIndian + ", basicSalary="
                + basicSalary + ", aadharNumber=" + aadharNumber + ", panNumber=" + panNumber + "]";
    }

}
