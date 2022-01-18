package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;

public class DesignationDAO implements DesignationDAOInterface {
    private final static String FILE_NAME = "designations.data";

    public void add(DesignationDTOInterface designationDTO) throws DAOException {
        if (designationDTO == null)
            throw new DAOException("Designation is null");
        if (designationDTO.getTitle() == null)
            throw new DAOException("Designation is null");
        String title = designationDTO.getTitle().trim();
        if (title.length() == 0)
            throw new DAOException("Length of designation is null");
        try {
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            String lastGeneratedCodeString = "";
            int lastGeneratedCode = 0;
            String recordCountString = "";
            int recordCount = 0;
            if (raf.length() == 0) {
                lastGeneratedCodeString += "0";
                while (lastGeneratedCodeString.length() < 10) {
                    lastGeneratedCodeString += " ";
                }
                recordCountString = "0";
                while (recordCountString.length() < 10) {
                    recordCountString += " ";
                }
                raf.writeBytes(lastGeneratedCodeString + "\n");
                raf.writeBytes(recordCountString + "\n");
            } else {
                lastGeneratedCodeString = raf.readLine().trim();
                recordCountString = raf.readLine().trim();
                lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
                recordCount = Integer.parseInt(recordCountString);
            }
            designationDTO.setCode(lastGeneratedCode+1);
            lastGeneratedCodeString = String.valueOf(lastGeneratedCode + 1);
            while (lastGeneratedCodeString.length() < 10)
                lastGeneratedCodeString += " ";
            recordCountString = String.valueOf(recordCount + 1);
            while (recordCountString.length() < 10)
                recordCountString += " ";

            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                if (fTitle.equalsIgnoreCase(title)) {
                    raf.close();
                    throw new DAOException("Designation " + title + " already exists");
                }
            }
            raf.writeBytes(lastGeneratedCodeString + "\n");
            raf.writeBytes(title + "\n");
            raf.seek(0);
            raf.writeBytes(lastGeneratedCodeString + "\n");
            raf.writeBytes(recordCountString + "\n");
            raf.close();
        } catch (Exception e) {
            throw new DAOException("Error: " + e.getMessage());
        }

    }

    public void update(DesignationDTOInterface designationDTO) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public void delete(int code) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public TreeSet<DesignationDTOInterface> getAll() throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public DesignationDTOInterface getByCode(int code) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public DesignationDTOInterface getByTitle(String title) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public boolean codeExists(int code) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public boolean titleExists(String title) throws DAOException {
        throw new DAOException("Not yet implemented");
    }

    public int getCount() throws DAOException {
        throw new DAOException("Not yet implemented");
    }
}
