package com.thinking.machines.hr.bl.interfaces.pojo;

import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.util.Date;

import com.thinking.machines.hr.bl.pojo.Employee;

public interface EmployeeInterface{
    public void setEmployeeId(String EmployeeId);
    public String getEmployeeId();
    public void setName(String name);
    public String getName();
    public void setDesignation(DesignationInterface designation);
    public DesignationInterface getDesignation();
    public void setDateOfBirth(Date dateOfBirth);
    public Date getDateOfBirth();
    public void setGender(char gender);
    public char getGender();
    public void setIsIndian(boolean isIndian);
    public boolean isIndian();
    public void setBasicSalary(BigDecimal basicSalary);
    public BigDecimal getBasicSalary();
    public void setPANNumber(String panNumber);
    public String getPANNumber();
    public void setAadharNumber(String aadharNumber);
    public String getAadharNumber();
    public Object clone() throws CloneNotSupportedException;
}
