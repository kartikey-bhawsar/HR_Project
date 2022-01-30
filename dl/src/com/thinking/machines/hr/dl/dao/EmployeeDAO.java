package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.EmployeeDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class EmployeeDAO implements EmployeeDAOInterface{

    private final static String FILE_NAME = "employees.data";

    public void add(EmployeeDTOInterface employeeDTO) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public void update(EmployeeDTOInterface employeeDTO) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public void delete(String employeeId) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public Set<EmployeeDTOInterface> getAll() throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean isDesignationAlloted(int designationCode) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean employeeIdExists(String employeeId) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean panNumberExists(String panNumber) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean aadharNumberExists(String aadharNumber) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public int getCount() throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public int getCountByEmployeeDesignation(int designationCode) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }
}
