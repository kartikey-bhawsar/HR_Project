package com.thinking.machines.hr.bl.managers;

import java.math.BigDecimal;
import java.rmi.dgc.VMID;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.DesignationManagerInterface;
import com.thinking.machines.hr.bl.interfaces.managers.EmployeeManagerInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.EmployeeInterface;
import com.thinking.machines.hr.bl.pojo.Designation;
import com.thinking.machines.hr.bl.pojo.Employee;
import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.dao.EmployeeDAO;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.dto.EmployeeDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dao.EmployeeDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;

public class EmployeeManager implements EmployeeManagerInterface {

    private Map<String, EmployeeInterface> employeeIdWiseEmployeeMap;
    private Map<String, EmployeeInterface> panNumberWiseEmployeeMap;
    private Map<String, EmployeeInterface> aadharCardNumberWiseEmployeeMap;
    private Set<EmployeeInterface> employeeSet;
    private static EmployeeManager employeeManager=null;

    private EmployeeManager() throws BLException
    {
        this.populateDataStructure();
    }

    public static EmployeeManager getEmployeeManager() throws BLException
    {
        if(employeeManager==null) employeeManager=new EmployeeManager();
        return employeeManager;
    }

    private void populateDataStructure() throws BLException
    {
        this.aadharCardNumberWiseEmployeeMap=new HashMap<>();
        this.employeeIdWiseEmployeeMap=new HashMap<>();
        this.panNumberWiseEmployeeMap=new HashMap<>();
        this.employeeSet=new TreeSet<>();
        try {
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            Set<EmployeeDTOInterface> employeesDTO=employeeDAO.getAll();
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();

            for(EmployeeDTOInterface employeeDTO: employeesDTO){
                EmployeeInterface employee=new Employee();
                employee.setEmployeeId(employeeDTO.getEmployeeId().trim());
                employee.setAadharNumber(employeeDTO.getAadharNumber());
                employee.setBasicSalary(employeeDTO.getBasicSalary());
                employee.setDateOfBirth(employeeDTO.getDateOfBirth());
                DesignationInterface designation = designationManager.getDesignationByCode(employeeDTO.getDesignationCode());
                employee.setDesignation(designation);
                employee.setGender(employeeDTO.getGender());
                employee.setIsIndian(employeeDTO.isIndian());
                employee.setName(employeeDTO.getName());
                employee.setPANNumber(employeeDTO.getPANNumber());

                employeeIdWiseEmployeeMap.put(employee.getEmployeeId().toUpperCase(),employee);
                panNumberWiseEmployeeMap.put(employee.getPANNumber().toUpperCase(),employee);
                aadharCardNumberWiseEmployeeMap.put(employee.getAadharNumber().toUpperCase(),employee);
                employeeSet.add(employee);
            }
        } catch (DAOException e) {
            BLException blException=new BLException();
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }

    public void addEmployee(EmployeeInterface employee) throws BLException
    {
        BLException blException=new BLException();
        if (employee == null)
            blException.setGenericException("employee is null");
        
        if(employee.getEmployeeId().trim().length()>0) blException.addException("employeeId", "Employee Id should be null");

        String name = employee.getName();
        if (name == null || name.trim().length()==0) blException.addException("name", "Name required");
        
        char gender = employee.getGender();
        
        BigDecimal basicSalary = employee.getBasicSalary();
        if (basicSalary == null) blException.addException("basicSalary", "Basic salary required");
        else{ 
            if (basicSalary.signum() == -1)
                blException.addException("basicSalary", "Basic salary should not be negative");
        }

        java.util.Date dateOfBirth = employee.getDateOfBirth();
        if (dateOfBirth == null) blException.addException("dateOfBirth", "Date of birth required");

        boolean isIndian = employee.isIndian();
        
        String panNumber = employee.getPANNumber();
        if (panNumber == null || panNumber.trim().length()==0) blException.addException("panNumber", "PAN Number required");

        String aadharNumber = employee.getAadharNumber();
        if (aadharNumber == null || aadharNumber.trim().length()==0) blException.addException("aadharNumber", "Aadhar card number required");
        
        DesignationInterface designation=employee.getDesignation();
        if(designation==null) 
        {
            blException.addException("designation", "Designation is null");
        }
        else{
            int designationCode=designation.getCode();
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            if(!designationManager.designationCodeExists(designationCode)) blException.addException("designation", "Invalid Designation");
        }

        if(panNumberWiseEmployeeMap.containsKey(panNumber)) blException.addException("panNumber", "PAN Number already exists");
        
        if(aadharCardNumberWiseEmployeeMap.containsKey(aadharNumber)) blException.addException("aadharNumber", "Aadhar card number already exists");
        
        if(blException.hasExceptions()) throw blException;

        try {
            EmployeeDTOInterface employeeDTO=new EmployeeDTO();
            employeeDTO.setAadharNumber(aadharNumber);
            employeeDTO.setBasicSalary(basicSalary);
            employeeDTO.setDateOfBirth(dateOfBirth);
            employeeDTO.setDesignationCode(employee.getDesignation().getCode());
            employeeDTO.setGender(gender);
            employeeDTO.setIsIndian(isIndian);
            employeeDTO.setName(name);
            employeeDTO.setPANNumber(panNumber);
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            employeeDAO.add(employeeDTO);
            employee.setEmployeeId(employeeDTO.getEmployeeId());
            Employee employeeClone=(Employee)employee.clone();
            panNumberWiseEmployeeMap.put(employee.getPANNumber(),employeeClone);
            aadharCardNumberWiseEmployeeMap.put(employee.getAadharNumber(),employeeClone);
            employeeIdWiseEmployeeMap.put(employeeClone.getEmployeeId(), employeeClone);
            employeeSet.add(employeeClone);
        } catch (DAOException | CloneNotSupportedException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }

    public void updateEmployee(EmployeeInterface employee) throws BLException
    {
        BLException blException=new BLException();
        if (employee == null)
            blException.setGenericException("employee is null");
        
        if(employee.getEmployeeId()==null) blException.addException("employeeId", "Employee Id required");
        String employeeId=employee.getEmployeeId().trim();
        if(employeeId.length()==0) blException.addException("employeeId", "Employee Id required");

        String name = employee.getName();
        if (name == null || name.trim().length()==0) blException.addException("name", "Name required");
        
        char gender = employee.getGender();
        
        BigDecimal basicSalary = employee.getBasicSalary();
        if (basicSalary == null) blException.addException("basicSalary", "Basic salary required");
        else{ 
            if (basicSalary.signum() == -1)
                blException.addException("basicSalary", "Basic salary should not be negative");
        }

        java.util.Date dateOfBirth = employee.getDateOfBirth();
        if (dateOfBirth == null) blException.addException("dateOfBirth", "Date of birth required");

        boolean isIndian = employee.isIndian();
        
        String panNumber = employee.getPANNumber();
        if (panNumber == null || panNumber.trim().length()==0) blException.addException("panNumber", "PAN Number required");

        String aadharNumber = employee.getAadharNumber();
        if (aadharNumber == null || aadharNumber.trim().length()==0) blException.addException("aadharNumber", "Aadhar card number required");
        
        DesignationInterface designation=employee.getDesignation();
        if(designation==null) 
        {
            blException.addException("designation", "Designation is null");
        }
        else{
            int designationCode=designation.getCode();
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            if(!designationManager.designationCodeExists(designationCode)) blException.addException("designation", "Invalid Designation");
        }
        
        if(blException.hasExceptions()) throw blException;
        try {
            if(employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase())==false)
            {
                blException.setGenericException("Employee with Employee Id: "+employeeId+" is not present");
                throw blException;
            }
            EmployeeDTOInterface employeeDTO=new EmployeeDTO();
            employeeDTO.setEmployeeId(employeeId);
            employeeDTO.setAadharNumber(aadharNumber);
            employeeDTO.setBasicSalary(basicSalary);
            employeeDTO.setDateOfBirth(dateOfBirth);
            employeeDTO.setDesignationCode(employee.getDesignation().getCode());
            employeeDTO.setGender(gender);
            employeeDTO.setIsIndian(isIndian);
            employeeDTO.setName(name);
            employeeDTO.setPANNumber(panNumber);
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            employeeDAO.update(employeeDTO);
            
            EmployeeInterface employee2=employeeIdWiseEmployeeMap.get(employeeId);
            employeeIdWiseEmployeeMap.remove(employeeId);
            aadharCardNumberWiseEmployeeMap.remove(employee2.getAadharNumber());
            panNumberWiseEmployeeMap.remove(employee2.getPANNumber());
            employeeSet.remove(employee2);
            
            EmployeeInterface employee3=new Employee();
            employee3.setAadharNumber(aadharNumber);
            employee3.setBasicSalary(basicSalary);
            employee3.setDateOfBirth(dateOfBirth);
            employee3.setDesignation(designation);
            employee3.setEmployeeId(employeeId);
            employee3.setGender(gender);
            employee3.setIsIndian(isIndian);
            employee3.setName(name);
            employee3.setPANNumber(panNumber);
            employeeIdWiseEmployeeMap.put(employee3.getEmployeeId(),employee3);
            aadharCardNumberWiseEmployeeMap.put(employee3.getAadharNumber(), employee3);
            panNumberWiseEmployeeMap.put(employee3.getPANNumber(), employee3);
            employeeSet.add(employee3);

        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
    public void removeEmployee(String employeeId) throws BLException
    {
        BLException blException=new BLException();
        if(employeeId==null)
        {
            blException.setGenericException("Invalid employee Id: "+employeeId);
            throw blException;
        }
        employeeId=employeeId.trim();
        if(employeeId.length()==0 || employeeIdWiseEmployeeMap.containsKey(employeeId)==false)
        {
            blException.setGenericException("Invalid employee Id: "+employeeId);
            throw blException;
        }
        try {
            EmployeeDAOInterface employeeDAO=new EmployeeDAO();
            employeeDAO.delete(employeeId);
            EmployeeInterface removableEmployee=employeeIdWiseEmployeeMap.get(employeeId);
            employeeIdWiseEmployeeMap.remove(employeeId);
            aadharCardNumberWiseEmployeeMap.remove(removableEmployee.getAadharNumber());
            panNumberWiseEmployeeMap.remove(removableEmployee.getPANNumber());
            employeeSet.remove(removableEmployee);
        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
    public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
    {
        BLException blException=new BLException();
        if(employeeId==null)
        {
            blException.setGenericException("Invalid employee Id: "+employeeId);
            throw blException;
        }
        employeeId=employeeId.trim();
        if(employeeId.length()==0 || employeeIdWiseEmployeeMap.containsKey(employeeId)==false)
        {
            blException.setGenericException("Invalid employee Id: "+employeeId);
            throw blException;
        }
        try {
            return (EmployeeInterface)employeeIdWiseEmployeeMap.get(employeeId).clone();
        } catch (Exception e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
    public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
    {
        BLException blException=new BLException();
        if(panNumber==null)
        {
            blException.setGenericException("Invalid PAN Number: "+panNumber);
            throw blException;
        }
        panNumber=panNumber.trim();
        if(panNumber.length()==0 || employeeIdWiseEmployeeMap.containsKey(panNumber.toUpperCase())==false)
        {
            blException.setGenericException("Invalid PAN Number: "+panNumber);
            throw blException;
        }
        try {
            return (EmployeeInterface)panNumberWiseEmployeeMap.get(panNumber.toUpperCase()).clone();
        } catch (Exception e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
    public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
    {
        BLException blException=new BLException();
        if(aadharCardNumber==null)
        {
            blException.setGenericException("Invalid Aadhar card Number: "+aadharCardNumber);
            throw blException;
        }
        aadharCardNumber=aadharCardNumber.trim();
        if(aadharCardNumber.length()==0 || employeeIdWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase())==false)
        {
            blException.setGenericException("Invalid Aadhar card Number: "+aadharCardNumber);
            throw blException;
        }
        try {
            return (EmployeeInterface)aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase()).clone();
        } catch (Exception e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
    public boolean employeeIdExists(String employeeId) throws BLException
    {
        if(employeeId==null) return false;
        return employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase());

    }
    public boolean employeePANNumberExists(String panNumber) throws BLException
    {
        return panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase());
    }
    public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
    {
        return aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase());
    }
    public int getEmployeeCount() throws BLException
    {
        return employeeSet.size();
    }
    public Set<EmployeeInterface> getEmployees() throws BLException
    {
        return employeeSet.stream().collect(Collectors.toCollection(()->new TreeSet<>()));
    }
    public Set<EmployeeInterface> getEmployeesByDesignationCode(int code) throws BLException
    {
        return employeeSet.stream().filter((e)->e.getDesignation().getCode()==code).collect(Collectors.toCollection(()->new TreeSet<>()));
    }
    public int getEmployeeCountByDesignationCode(int code) throws BLException
    {
        return employeeSet.stream().filter((e)->e.getDesignation().getCode()==code).collect(Collectors.toList()).size();
    }
    public boolean isDesignationAlloted(int code) throws BLException
    {
        BLException blException=new BLException();
        try {
            DesignationDAOInterface designationDAO=new DesignationDAO();
            if(designationDAO.codeExists(code)==false)
            {
                blException.setGenericException("Invalid Code "+code);
                throw blException;
            }
            return employeeSet.stream().anyMatch(e->e.getDesignation().getCode()==code);
        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }
}
