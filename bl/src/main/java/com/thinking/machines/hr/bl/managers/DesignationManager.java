package com.thinking.machines.hr.bl.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.DesignationManagerInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;
import com.thinking.machines.hr.bl.pojo.Designation;
import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesignationManager implements DesignationManagerInterface {

    private Map<Integer, DesignationInterface> codeWiseDesignationMap;
    private Map<String, DesignationInterface> titleWiseDesignationMap;
    private Set<DesignationInterface> designationSet;
    private static DesignationManager designationManager = null;

    private DesignationManager() throws BLException {
        this.populateDataStructure();
    }

    public static DesignationManager getDesignationManager() throws BLException {
        if (designationManager == null)
            designationManager = new DesignationManager();
        return designationManager;
    }

    private void populateDataStructure() throws BLException {
        codeWiseDesignationMap = new HashMap<>();
        titleWiseDesignationMap = new HashMap<>();
        designationSet = new TreeSet<>();
        try {
            DesignationDAOInterface designationDAO = new DesignationDAO();
            Set<DesignationDTOInterface> dlDesignations = designationDAO.getAll();
            dlDesignations.forEach((dlDesignation) -> {
                DesignationInterface designation = new Designation();
                designation.setCode(dlDesignation.getCode());
                designation.setTitle(dlDesignation.getTitle());
                codeWiseDesignationMap.put(designation.getCode(), designation);
                titleWiseDesignationMap.put(designation.getTitle().toUpperCase(), designation);
                designationSet.add(designation);
            });

        } catch (DAOException e) {
            BLException blException = new BLException();
            blException.setGenericException(e.getMessage());
            throw blException;
        }
    }

    @Override
    public void addDesignation(DesignationInterface designation) throws BLException {
        BLException blException = new BLException();
        if (designation == null) {
            blException.setGenericException("Designation required");
            throw blException;
        }
        int code = designation.getCode();
        String title = designation.getTitle().trim();
        if (code != 0) {
            blException.addException("code", "Code should be zero");
        }
        if (title == null || title.length() == 0) {
            blException.addException("title", "Title required");
        }
        if (titleWiseDesignationMap.containsKey(title.toUpperCase())) {
            blException.addException("title", "Designation " + title + " already exists");
        }
        if (blException.hasExceptions())
            throw blException;
        try {
            DesignationDTOInterface designationDTO = new DesignationDTO();
            designationDTO.setTitle(title);
            DesignationDAOInterface designationDAO = new DesignationDAO();
            designationDAO.add(designationDTO);
            code = designationDTO.getCode();
            designation.setCode(code);

            Designation dsDesignation = new Designation();
            dsDesignation.setCode(code);
            dsDesignation.setTitle(title);

            codeWiseDesignationMap.put(code, dsDesignation);
            titleWiseDesignationMap.put(title.toUpperCase(), dsDesignation);
            designationSet.add(designation);
        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }

    }

    @Override
    public boolean designationCodeExists(int code) throws BLException {
        if(code<=0) 
        {
            return false;
        }
        return codeWiseDesignationMap.containsKey(code);
        
    }

    @Override
    public boolean designationTitleExists(String title) throws BLException {
        if(title==null) 
        {
            return false;
        }
        title=title.trim();
        return codeWiseDesignationMap.containsKey(title.toUpperCase());
    }

    @Override
    public DesignationInterface getDesignationByCode(int code) throws BLException {
        BLException blException=new BLException();
        if(code<=0) 
        {
            blException.addException("code", "Invalid code: "+code);
            throw blException;
        }
        if(!codeWiseDesignationMap.containsKey(code))
        {
            blException.addException("code", "Invalid code: "+code);
            throw blException;
        }
        return codeWiseDesignationMap.get(code);
    }

    @Override
    public DesignationInterface getDesignationByTitle(String title) throws BLException {
        BLException blException=new BLException();
        if(title==null) 
        {
            blException.addException("title", "Invalid designation: "+title);
            throw blException;
        }
        if(!titleWiseDesignationMap.containsKey(title.toUpperCase()))
        {
            blException.addException("title", "Invalid designation: "+title);
            throw blException;
        }
        return titleWiseDesignationMap.get(title);
    }

    @Override
    public int getDesignationCount() throws BLException {
        return designationSet.size();
    }

    @Override
    public Set<DesignationInterface> getDesignations() throws BLException {
        return designationSet.stream().collect(Collectors.toCollection(()->new TreeSet<>()));
    }

    @Override
    public void removeDesignation(int code) throws BLException {
        BLException blException = new BLException();
        if (code<= 0) {
            blException.addException("code", "Invalid code: "+code);
        }
        if(!codeWiseDesignationMap.containsKey(code)) blException.addException("code", "Invalid code: "+code);
        if (blException.hasExceptions())
            throw blException;
        try {
            
            DesignationDAOInterface designationDAO = new DesignationDAO();
            designationDAO.delete(code);
            
            DesignationInterface removabalDesignation=codeWiseDesignationMap.get(code);
            titleWiseDesignationMap.remove(removabalDesignation.getTitle().toUpperCase());
            codeWiseDesignationMap.remove(code);
            designationSet.remove(removabalDesignation);
        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }

    }

    @Override
    public void updateDesignation(DesignationInterface designation) throws BLException {
        BLException blException = new BLException();
        if (designation == null) {
            blException.setGenericException("Designation required");
            throw blException;
        }
        int code = designation.getCode();
        String title = designation.getTitle().trim();
        if (code<= 0) {
            blException.addException("code", "Invalid code: "+code);
        }
        if (title == null || title.length() == 0) {
            blException.addException("title", "Title required");
        }
        if(!codeWiseDesignationMap.containsKey(code)) blException.addException("code", "Invalid code: "+code);
        if (titleWiseDesignationMap.containsKey(title.toUpperCase())) {
            blException.addException("title", "Designation " + title + " already exists");
        }
        if (blException.hasExceptions())
            throw blException;
        try {
            DesignationDTOInterface designationDTO=new DesignationDTO();
            designationDTO.setCode(code);
            designationDTO.setTitle(title);
            
            DesignationDAOInterface designationDAO = new DesignationDAO();
            designationDAO.update(designationDTO);
            
            DesignationInterface removabalDesignation=codeWiseDesignationMap.get(code);
            titleWiseDesignationMap.remove(removabalDesignation.getTitle().toUpperCase());
            codeWiseDesignationMap.remove(code);
            designationSet.remove(removabalDesignation);
            
            Designation dsDesignation=new Designation();
            dsDesignation.setCode(code);
            dsDesignation.setTitle(title);

            codeWiseDesignationMap.put(code, dsDesignation);
            titleWiseDesignationMap.put(title, dsDesignation);
            designationSet.add(dsDesignation);
        } catch (DAOException e) {
            blException.setGenericException(e.getMessage());
            throw blException;
        }

    }

}
