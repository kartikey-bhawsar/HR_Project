package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class DesignationDAO implements DesignationDAOInterface
{
    public void add(DesignationDTOInterface designationDTO) throws DAOException
    {
        
    }

    public void update(DesignationDTOInterface designationDTO) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public void delete(int code) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public TreeSet<DesignationDTOInterface> getAll() throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public DesignationDTOInterface getByCode(int code) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public DesignationDTOInterface getByTitle(String title) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean codeExists(int code) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public boolean titleExists(String title) throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }

    public int getCount() throws DAOException
    {
        throw new DAOException("Not yet implemented");
    }
}
