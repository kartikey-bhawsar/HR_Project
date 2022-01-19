package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;

import java.io.File;
import java.io.IOException;
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
            designationDTO.setCode(lastGeneratedCode + 1);
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
            raf.writeBytes((lastGeneratedCode+1) + "\n");
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
        if (designationDTO == null)
            throw new DAOException("Invalid Input");
        if (designationDTO.getTitle() == null)
            throw new DAOException("Invalid Input");
        String title = designationDTO.getTitle().trim();
        int code=designationDTO.getCode();
        if(code<=0) throw new DAOException("Invalid code");
        if (title.length() == 0)
            throw new DAOException("Length of designation is null");
        try {
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                throw new DAOException("Invalid Input");
            }
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length()) // to check if the title already exists
            {
                raf.readLine();
                String ftitle=raf.readLine();
                if(ftitle.equalsIgnoreCase(title))
                {
                    raf.close();
                    throw new DAOException("Title "+title+ "already exists");
                }
            }
            File tmpFile=new File("tmp.txt");
            RandomAccessFile rafTmp=new RandomAccessFile(tmpFile, "rw");
            raf.seek(0);
            rafTmp.writeBytes(raf.readLine()+"\n");
            rafTmp.writeBytes(raf.readLine()+"\n");
            boolean found=false;
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine());
                String fTitle=raf.readLine();
                if (fCode==code) {
                    found=true;
                    rafTmp.writeBytes(fCode+"\n");
                    rafTmp.writeBytes(title+"\n");
                }
                else{
                    rafTmp.writeBytes(fCode+"\n");
                    rafTmp.writeBytes(fTitle+"\n");
                }
            }
            if(!found)
            {
                rafTmp.setLength(0);
                rafTmp.close();
                raf.close();
                throw new DAOException("Invalid code "+code);
            }
            raf.seek(0);
            rafTmp.seek(0);
            while(rafTmp.getFilePointer()<rafTmp.length())
            {
                raf.writeBytes(rafTmp.readLine()+"\n");
            }
            raf.setLength(rafTmp.length());
            rafTmp.close();
            tmpFile.delete();
            raf.close();
        } catch (Exception e) {
            throw new DAOException("Error: " + e.getMessage());
        }
    }

    public void delete(int code) throws DAOException {
        if(code<=0) throw new DAOException("Invalid code");
        try {
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                throw new DAOException("Invalid Input");
            }
            File tmpFile=new File("tmp.txt");
            RandomAccessFile rafTmp=new RandomAccessFile(tmpFile, "rw");
            rafTmp.writeBytes(raf.readLine()+"\n");
            rafTmp.writeBytes(raf.readLine()+"\n");
            boolean found=false;
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine());
                String fTitle=raf.readLine();
                if (fCode==code) {
                    found=true;
                }
                else{
                    rafTmp.writeBytes(fCode+"\n");
                    rafTmp.writeBytes(fTitle+"\n");
                }
            }
            if(!found)
            {
                rafTmp.setLength(0);
                rafTmp.close();
                raf.close();
                throw new DAOException("Invalid code "+code);
            }
            raf.seek(0);
            rafTmp.seek(0);
            int lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
            int recordCount=Integer.parseInt(raf.readLine().trim());
            lastGeneratedCode--;
            recordCount--;
            String lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
            while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
            String recordCountString=String.valueOf(recordCount);
            while(recordCountString.length()<10) recordCountString+=" ";
            rafTmp.writeBytes(lastGeneratedCodeString+"\n");
            rafTmp.writeBytes(recordCountString+"\n");
            rafTmp.seek(0);
            raf.seek(0);
            while(rafTmp.getFilePointer()<rafTmp.length())
            {
                raf.writeBytes(rafTmp.readLine()+"\n");
            }
            raf.setLength(rafTmp.length());
            rafTmp.close();
            tmpFile.delete();
            raf.close();
        } catch (Exception e) {
            throw new DAOException("Error: " + e.getMessage());
        }
    }

    public Set<DesignationDTOInterface> getAll() throws DAOException {
        Set<DesignationDTOInterface> designations = new TreeSet<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists())
                return designations;
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                return designations;
            }
            raf.readLine(); // for last generated code
            raf.readLine(); // for record count
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                DesignationDTOInterface dto = new DesignationDTO();
                dto.setCode(fCode);
                dto.setTitle(fTitle);
                designations.add(dto);
            }
            raf.close();
            return designations;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public DesignationDTOInterface getByCode(int code) throws DAOException {
        try {
            if (code <= 0)
                throw new DAOException("Invalid code");
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                throw new DAOException("Invalid code");
            }
            raf.readLine();
            raf.readLine();
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                if (fCode == code) {
                    DesignationDTOInterface dto = new DesignationDTO();
                    dto.setCode(code);
                    dto.setTitle(fTitle);
                    raf.close();
                    return dto;
                }
            }
            raf.close();
            throw new DAOException("Invalid code " + code);
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public DesignationDTOInterface getByTitle(String title) throws DAOException {
        try {
            if (title == null || title.trim().length() == 0)
                throw new DAOException("Invalid title");
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                throw new DAOException("Invalid title");
            }
            raf.readLine();
            raf.readLine();
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                if (fTitle.equalsIgnoreCase(title)) {
                    DesignationDTOInterface dto = new DesignationDTO();
                    dto.setCode(fCode);
                    dto.setTitle(fTitle);
                    raf.close();
                    return dto;
                }
            }
            raf.close();
            throw new DAOException("Invalid title " + title);
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public boolean codeExists(int code) throws DAOException {
        try {
            if (code <= 0)
                return false;
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                return false;
            }
            raf.readLine();
            raf.readLine();
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                if (fCode == code) {
                    raf.close();
                    return true;
                }
            }
            raf.close();
            return false;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public boolean titleExists(String title) throws DAOException {
        try {
            if (title == null || title.trim().length() == 0)
                return false;
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                return false;
            }
            raf.readLine();
            raf.readLine();
            while (raf.getFilePointer() < raf.length()) {
                int fCode = Integer.parseInt(raf.readLine().trim());
                String fTitle = raf.readLine();
                if (fTitle.equalsIgnoreCase(title)) {
                    raf.close();
                    return true;
                }
            }
            raf.close();
            return false;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public int getCount() throws DAOException {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists())
                return 0;
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (raf.length() == 0) {
                raf.close();
                return 0;
            }
            raf.readLine();
            int count = Integer.parseInt(raf.readLine().trim());
            raf.close();
            return count;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }
}
