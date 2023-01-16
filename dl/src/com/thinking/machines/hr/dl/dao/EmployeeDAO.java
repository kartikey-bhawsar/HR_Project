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
            employeeDTO.setEmployeeId(lastGeneratedCodeString.trim()); // Important
        } catch (FileNotFoundException fnfe) {
            throw new DAOException(fnfe.getMessage());
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public void update(EmployeeDTOInterface employeeDTO) throws DAOException {
        if (employeeDTO == null)
            throw new DAOException("employee is null");
        String employeeId=employeeDTO.getEmployeeId().trim();
        if(employeeId==null || employeeId.length()==0) throw new DAOException("Invalid employee id: "+employeeId);
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
            File file=new File(FILE_NAME);
            if(!file.exists()) throw new DAOException("Invalid Employee Id: "+employeeId);
            RandomAccessFile raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+employeeId);
            }
            raf.readLine();
            raf.readLine();
            boolean isNeedToBeUpdated=false;
            File file2=new File("tmp.tmp");
            if(file2.exists()) file2.delete();
            long employeeUpdatePositionInFile=0;
            RandomAccessFile tmpRaf=new RandomAccessFile(file2, "rw");
            while(raf.getFilePointer()<raf.length())
            {
                if(employeeId.equalsIgnoreCase(raf.readLine().trim()))
                {
                    isNeedToBeUpdated=true;
                    employeeUpdatePositionInFile=raf.getFilePointer();
                    break;
                }
                for(int i=0;i<8;i++) raf.readLine();
            }
            if(isNeedToBeUpdated==false)
            {
                raf.close();
                tmpRaf.close();
                file2.delete();
                throw new DAOException("No employee is present with this employee id: "+employeeId);
            }
            for(int i=0;i<8;i++) raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                tmpRaf.writeBytes(raf.readLine()+"\n");
            }
            raf.seek(employeeUpdatePositionInFile);
            raf.writeBytes(name + "\n");
            raf.writeBytes(designationCode + "\n");
            SimpleDateFormat smp = new SimpleDateFormat("dd/MM/yyyy");
            raf.writeBytes(smp.format(dateOfBirth) + "\n");
            raf.writeBytes(gender + "\n");
            raf.writeBytes(isIndian + "\n");
            raf.writeBytes(basicSalary.toPlainString() + "\n");
            raf.writeBytes(panNumber + "\n");
            raf.writeBytes(aadharNumber + "\n");
            tmpRaf.seek(0);
            while(tmpRaf.getFilePointer()<tmpRaf.length())
            {
                raf.writeBytes(tmpRaf.readLine()+"\n");
            }
            raf.setLength(raf.getFilePointer());
            tmpRaf.close();
            file2.delete();
            raf.close();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void delete(String employeeId) throws DAOException {
        employeeId=employeeId.trim();
        if(employeeId==null || employeeId.length()==0) throw new DAOException("Invalid Employee Id: "+employeeId);
        try {
            File file=new File(FILE_NAME);
            if(!file.exists()) throw new DAOException("Invalid Employee Id: "+employeeId);
            RandomAccessFile raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+employeeId);
            };
            raf.readLine();
            int recordCount=Integer.parseInt(raf.readLine().trim());
            long employeeDeletePositionInFile=0;
            boolean isNeedToBeDeleted=false;
            while(raf.getFilePointer()<raf.length())
            {
                employeeDeletePositionInFile=raf.getFilePointer();
                if(raf.readLine().trim().equalsIgnoreCase(employeeId))
                {
                    isNeedToBeDeleted=true;
                    for(int i=0;i<8;i++) raf.readLine();
                    break;
                }
                for(int i=0;i<8;i++) raf.readLine();
            }
            if(isNeedToBeDeleted==false)
            {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+employeeId);
            }
            if(recordCount==1)
            {
                raf.setLength(0);
                raf.close();
                return;
            }
            File file2=new File("tmp.tmp");
            if(file2.exists()) file2.delete();
            RandomAccessFile tmpRaf=new RandomAccessFile(file2, "rw");
            // tmpRaf.seek(0);
            while(raf.getFilePointer()<raf.length())
            {
                tmpRaf.writeBytes(raf.readLine()+"\n");
            }
            tmpRaf.seek(0);
            raf.seek(employeeDeletePositionInFile);
            while(tmpRaf.getFilePointer()<tmpRaf.length())
            {
                raf.writeBytes(tmpRaf.readLine()+"\n");
            }
            raf.setLength(raf.getFilePointer());
            raf.seek(0);
            raf.readLine();
            raf.writeBytes(String.format("%-12s",recordCount-1));
            tmpRaf.close();
            raf.close();
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
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
        try {
            if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid Designation code: " + designationCode);
            File file = new File(FILE_NAME);
            if(file.exists()==false) return false;
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            if (raf.length() == 0) {
                raf.close();
                return false;
            }
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                raf.readLine();
                raf.readLine();
                int fDesignationCode=Integer.parseInt(raf.readLine());
                if(fDesignationCode==designationCode) return true;
                for(int i=0;i<6;i++) raf.readLine();
            }
            raf.close();
            return false;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }

    public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException {
        employeeId=employeeId.trim();
        if(employeeId==null || employeeId.length()==0) throw new DAOException("Invalid Employee Id: "+employeeId);
        try {
            File file=new File(FILE_NAME);
            if(!file.exists()) throw new DAOException("Invalid Employee Id: "+employeeId);
            RandomAccessFile raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+employeeId);
            }
            EmployeeDTOInterface dto = new EmployeeDTO();
            raf.readLine();
            raf.readLine();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            while(raf.getFilePointer()<raf.length())
            {
                if(raf.readLine().trim().equalsIgnoreCase(employeeId))
                {
                    dto.setEmployeeId(employeeId);
                    dto.setName(raf.readLine());
                    dto.setDesignationCode(Integer.parseInt(raf.readLine()));
                    dto.setDateOfBirth(sdf.parse(raf.readLine()));
                    dto.setGender(raf.readLine().charAt(0));
                    dto.setIsIndian(Boolean.parseBoolean(raf.readLine()));
                    dto.setBasicSalary(new BigDecimal(raf.readLine()));
                    dto.setPANNumber(raf.readLine());
                    dto.setAadharNumber(raf.readLine());
                    raf.close();
                    return dto;
                } 
                else
                {
                    int n=0;
                    while(n<8)
                    {
                        raf.readLine();
                        n++;
                    }
                }
            }
            raf.close();
            throw new DAOException("Invalid Employee Id: "+employeeId);

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException {
        panNumber=panNumber.trim();
        if(panNumber==null || panNumber.length()==0) throw new DAOException("Invalid PAN: "+panNumber);
        try {
            File file=new File(FILE_NAME);
            if(!file.exists()) throw new DAOException("Invalid Employee Id: "+panNumber);
            RandomAccessFile raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+panNumber);
            }
            EmployeeDTOInterface dto = new EmployeeDTO();
            raf.readLine();
            raf.readLine();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            while(raf.getFilePointer()<raf.length())
            {
                String employeeId=raf.readLine();
                String name=raf.readLine();
                int designationCode=Integer.parseInt(raf.readLine());
                Date dateOfBirth=sdf.parse(raf.readLine());
                char gender=raf.readLine().charAt(0);
                boolean isIndian=Boolean.parseBoolean(raf.readLine());
                BigDecimal basicSalary=new BigDecimal(raf.readLine());
                String fPanNumber=raf.readLine();
                String aadharNumber=raf.readLine();
                if(fPanNumber.equalsIgnoreCase(panNumber))
                {
                    dto.setEmployeeId(employeeId);
                    dto.setName(name);
                    dto.setDesignationCode(designationCode);
                    dto.setDateOfBirth(dateOfBirth);
                    dto.setGender(gender);
                    dto.setIsIndian(isIndian);
                    dto.setBasicSalary(basicSalary);
                    dto.setPANNumber(panNumber);
                    dto.setAadharNumber(aadharNumber);
                    raf.close();
                    return dto;
                } 
            }
            raf.close();
            throw new DAOException("Invalid Employee Id: "+panNumber);

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException {
        aadharNumber=aadharNumber.trim();
        if(aadharNumber==null || aadharNumber.length()==0) throw new DAOException("Invalid aadhar no: "+aadharNumber);
        try {
            File file=new File(FILE_NAME);
            if(!file.exists()) throw new DAOException("Invalid Employee Id: "+aadharNumber);
            RandomAccessFile raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                raf.close();
                throw new DAOException("Invalid Employee Id: "+aadharNumber);
            }
            EmployeeDTOInterface dto = new EmployeeDTO();
            raf.readLine();
            raf.readLine();
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            while(raf.getFilePointer()<raf.length())
            {
                String employeeId=raf.readLine();
                String name=raf.readLine();
                int designationCode=Integer.parseInt(raf.readLine());
                Date dateOfBirth=sdf.parse(raf.readLine());
                char gender=raf.readLine().charAt(0);
                boolean isIndian=Boolean.parseBoolean(raf.readLine());
                BigDecimal basicSalary=new BigDecimal(raf.readLine());
                String panNumber=raf.readLine();
                String fAadharNumber=raf.readLine();
                if(fAadharNumber.equalsIgnoreCase(aadharNumber))
                {
                    dto.setEmployeeId(employeeId);
                    dto.setName(name);
                    dto.setDesignationCode(designationCode);
                    dto.setDateOfBirth(dateOfBirth);
                    dto.setGender(gender);
                    dto.setIsIndian(isIndian);
                    dto.setBasicSalary(basicSalary);
                    dto.setPANNumber(panNumber);
                    dto.setAadharNumber(aadharNumber);
                    raf.close();
                    return dto;
                } 
            }
            raf.close();
            throw new DAOException("Invalid Employee Id: "+aadharNumber);

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean employeeIdExists(String employeeId) throws DAOException {
        employeeId=employeeId.trim();
        File file=null;
        RandomAccessFile raf=null;
        if(employeeId==null || employeeId.length()==0) return false;
        try {
            file=new File(FILE_NAME);
            if(!file.exists()) return false;
            raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                return false;
            }
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                if(raf.readLine().trim().equalsIgnoreCase(employeeId))
                {
                    return true;
                } 
                else
                {
                    int n=0;
                    while(n<8)
                    {
                        raf.readLine();
                        n++;
                    }
                }
            }
            return false;

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }finally{
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean panNumberExists(String panNumber) throws DAOException {
        panNumber=panNumber.trim();
        File file=null;
        RandomAccessFile raf=null;
        if(panNumber==null || panNumber.length()==0) return false;
        try {
            file=new File(FILE_NAME);
            if(!file.exists()) return false;
            raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                return false;
            }
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                int n=0;
                while(n<7)
                {
                    raf.readLine();
                    n++;
                }
                if(raf.readLine().equalsIgnoreCase(panNumber))
                {
                    return true;
                } 
                raf.readLine();
            }
            return false;

        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }finally{
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean aadharNumberExists(String aadharNumber) throws DAOException {
        aadharNumber=aadharNumber.trim();
        File file=null;
        RandomAccessFile raf=null;
        if(aadharNumber==null || aadharNumber.length()==0) return false;
        try {
            file=new File(FILE_NAME);
            if(!file.exists()) return false;
            raf=new RandomAccessFile(file, "rw");
            if(raf.length()==0) {
                return false;
            }
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                int n=0;
                while(n<8)
                {
                    raf.readLine();
                    n++;
                }
                if(raf.readLine().equalsIgnoreCase(aadharNumber))
                {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new DAOException(e.getMessage());
        }finally{
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        try {
            if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid Designation code: " + designationCode);
            File file = new File(FILE_NAME);
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            if (raf.length() == 0) {
                raf.close();
                return 0;
            }
            int count = 0;
            raf.readLine();
            raf.readLine();
            while(raf.getFilePointer()<raf.length())
            {
                raf.readLine();
                raf.readLine();
                int fDesignationCode=Integer.parseInt(raf.readLine());
                for(int i=0;i<6;i++) raf.readLine();
                if(fDesignationCode==designationCode) count++;
            }
            raf.close();
            return count;
        } catch (IOException ioe) {
            throw new DAOException(ioe.getMessage());
        }
    }
}
