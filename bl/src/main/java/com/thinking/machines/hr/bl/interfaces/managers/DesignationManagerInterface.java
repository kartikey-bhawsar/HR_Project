package com.thinking.machines.hr.bl.interfaces.managers;

import java.util.Set;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;

public interface DesignationManagerInterface {
    public void addDesignation(DesignationInterface designation) throws BLException;
    public void updateDesignation(DesignationInterface designation) throws BLException;
    public void removeDesignation(int code) throws BLException;
    public DesignationInterface getDesignationByCode(int code) throws BLException;
    public DesignationInterface getDesignationByTitle(String title) throws BLException;
    public boolean designationCodeExists(int code) throws BLException;
    public boolean designationTitleExists(String title) throws BLException;
    public int getDesignationCount() throws BLException;
    public Set<DesignationInterface> getDesignations() throws BLException;
}
