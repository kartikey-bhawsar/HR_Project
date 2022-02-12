package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.dto.EmployeeDTO;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmployeeDAO implements EmployeeDAOInterface {

    private final static String FILE_NAME = "employees.data";

    public void add(EmployeeDTOInterface employeeDTO) throws DAOException {
        if (employeeDTO == null)
            throw new DAOException("employee is null");
        String name = employeeDTO.getName();
        if (name == null)
            throw new DAOException("Name is null");
        name = name.trim();
        if (name.length() == 0)
            throw new DAOException("Length of name is zero");
        char gender = employeeDTO.getGender();
        BigDecimal basicSalary = employeeDTO.getBasicSalary();
        if (basicSalary == null)
            throw new DAOException("Basic salary is null");
        if (basicSalary.signum() == -1)
            throw new DAOException("Basic salary is negative");
        Date dateOfBirth = employeeDTO.getDateOfBirth();
        if (dateOfBirth == null)
            throw new DAOException("Date of birth is null");
        boolean isIndian = employeeDTO.isIndian();
        String panNumber = employeeDTO.getPANNumber();
        if (panNumber == null)
            throw new DAOException("PAN number is null");
        panNumber = panNumber.trim();
        if (panNumber.length() == 0)
            throw new DAOException("PAN number length is zero");
        String aadharNumber = employeeDTO.getAadharNumber();
        if (aadharNumber == null)
            throw new DAOException("Aadhar number is null");
        aadharNumber = aadharNumber.trim();
        if (aadharNumber.length() == 0)
            throw new DAOException("Aadhar number length is zero");
        int designationCode = employeeDTO.getDesignationCode();
        DesignationDAOInterface dao = new DesignationDAO();
        if (!dao.codeExists(designationCode))
            throw new DAOException("Invalid designation code " + designationCode);
        try {
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            int lastGeneratedCode = 1000000;
            String lastGeneratedCodeString = "";
            int recordCount = 0;
            String recordCountString = "";
            if (raf.length() == 0) {
                lastGeneratedCodeString = String.format("%-12s", lastGeneratedCode);
                raf.writeBytes(lastGeneratedCodeString + "\n");
                recordCountString = String.format("%-12s", recordCount);
                raf.writeBytes(recordCountString + "\n");
            } else {
                lastGeneratedCode = Integer.parseInt(raf.readLine().trim());
                recordCount = Integer.parseInt(raf.readLine().trim());
            }
            while (raf.getFilePointer() < raf.length()) {
                for (int i = 0; i < 7; i++)
                    raf.readLine();
                String fPanNumber = raf.readLine();
                String fAadharNumber = raf.readLine();
                if (fPanNumber.equalsIgnoreCase(panNumber)) {
                    raf.close();
                    throw new DAOException("PAN Number already exists");
                }
                if (fAadharNumber.equalsIgnoreCase(aadharNumber)) {
                    raf.close();
                    throw new DAOException("Aadhar number already exists");
                }
            }
            lastGeneratedCodeString = String.format("%-12s", lastGeneratedCode + 1);
            recordCountString = String.format("%-12s", recordCount + 1);
            raf.writeBytes(lastGeneratedCodeString + "\n");
            raf.writeBytes(name + "\n");
            raf.writeBytes(designationCode + "\n");
            SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
            raf.writeBytes(smp.format(dateOfBirth) + "\n");
            raf.writeBytes(gender + "\n");
            raf.writeBytes(isIndian + "\n");
            raf.writeBytes(basicSalary.toPlainString() + "\n");
            raf.writeBytes(panNumber + "\n");
            raf.writeBytes(aadharNumber + "\n");
            raf.seek(0);
            raf.writeBytes(lastGeneratedCodeString + "\n");
            raf.writeBytes(recordCountString + "\n");
            raf.close();
            employeeDTO.setEmployeeId(lastGeneratedCodeString); // Important
        } catch (FileNotFoundException fnfe) {
            throw new DAOException(fnfe.getMessage());
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public void update(EmployeeDTOInterface employeeDTO) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public void delete(String employeeId) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public Set<EmployeeDTOInterface> getAll() throws DAOException {
        try {
            Set<EmployeeDTOInterface> employees = new TreeSet<>();
            File file = new File(FILE_NAME);
            if (!(file.exists()))
                return employees;
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.readLine();
            raf.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (raf.getFilePointer() < raf.length()) {
                EmployeeDTOInterface eDto = new EmployeeDTO();
                eDto.setEmployeeId(raf.readLine());
                eDto.setName(raf.readLine());
                eDto.setDesignationCode(Integer.parseInt(raf.readLine()));
                eDto.setDateOfBirth(sdf.parse(raf.readLine()));
                eDto.setGender(raf.readLine().charAt(0));
                eDto.setIsIndian(Boolean.parseBoolean(raf.readLine()));
                eDto.setBasicSalary(new BigDecimal(raf.readLine()));
                eDto.setPANNumber(raf.readLine());
                eDto.setAadharNumber(raf.readLine());
                employees.add(eDto);
            }
            raf.close();
            return employees;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        } catch (ParseException pe) {
            throw new DAOException(pe.getMessage());
        }
    }

    public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException {
        try {
            Set<EmployeeDTOInterface> employees = new TreeSet<>();
            File file = new File(FILE_NAME);
            if (!(file.exists()))
                return employees;
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.readLine();
            raf.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fEmployeeId = "";
            String fName = "";
            int fDesignationCode = 0;
            while (raf.getFilePointer() < raf.length()) {
                fEmployeeId = raf.readLine();
                fName = raf.readLine();
                fDesignationCode = Integer.parseInt(raf.readLine());
                if (fDesignationCode != designationCode) {
                    for (int i = 0; i < 6; i++) {
                        raf.readLine();
                    }
                } else {
                    EmployeeDTOInterface eDto = new EmployeeDTO();
                    eDto.setEmployeeId(fEmployeeId);
                    eDto.setName(fName);
                    eDto.setDesignationCode(fDesignationCode);
                    eDto.setDateOfBirth(sdf.parse(raf.readLine()));
                    eDto.setGender(raf.readLine().charAt(0));
                    eDto.setIsIndian(Boolean.parseBoolean(raf.readLine()));
                    eDto.setBasicSalary(new BigDecimal(raf.readLine()));
                    eDto.setPANNumber(raf.readLine());
                    eDto.setAadharNumber(raf.readLine());
                    employees.add(eDto);
                }
            }
            raf.close();
            return employees;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        } catch (ParseException pe) {
            throw new DAOException(pe.getMessage());
        }
    }

    public boolean isDesignationAlloted(int designationCode) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public boolean employeeIdExists(String employeeId) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public boolean panNumberExists(String panNumber) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public boolean aadharNumberExists(String aadharNumber) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public int getCount() throws DAOException {
        try {
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            if (raf.length() == 0) {
                raf.close();
                return 0;
            }
            int recordCount = 0;
            raf.readLine();
            recordCount = Integer.parseInt(raf.readLine().trim());
            raf.close();
            return recordCount;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public int getCountByEmployeeDesignation(int designationCode) throws DAOException {
        throw new DAOException("Not yet implemented");
    }
}
