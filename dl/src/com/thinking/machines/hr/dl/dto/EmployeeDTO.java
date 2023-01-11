package com.thinking.machines.hr.dl.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.thinking.machines.hr.dl.interfaces.dto.*;

public class EmployeeDTO implements EmployeeDTOInterface {
    private String employeeId;
    private String name;
    private int designationCode;
    private Date dateOfBirth;
    private char gender;
    private boolean isIndian;
    private BigDecimal basicSalary;
    private String aadharNumber;
    private String panNumber;

    public EmployeeDTO() {
        this.employeeId = "";
        this.name = "";
        this.designationCode = 0;
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

    public void setDesignationCode(int designationCode) {
        this.designationCode = designationCode;
    }

    public int getDesignationCode() {
        return this.designationCode;
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
        if(!(other instanceof EmployeeDTO)) return false;
        EmployeeDTO dto=(EmployeeDTO)other;
        return this.employeeId.equalsIgnoreCase(dto.getEmployeeId());
    }

    public int compareTo(EmployeeDTOInterface other)
    {
        return this.employeeId.compareToIgnoreCase(other.getEmployeeId());
    }

    public int hashCode()
    {
        return this.employeeId.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return "EmployeeDTO [employeeId=" + employeeId + ", name=" + name + ", designationCode=" + designationCode
                + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", isIndian=" + isIndian + ", basicSalary="
                + basicSalary + ", aadharNumber=" + aadharNumber + ", panNumber=" + panNumber + "]";
    }

}
