package com.thinking.machines.hr.dl.dto;

import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesignationDTO implements DesignationDTOInterface{
    int code;
    String title;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public boolean equals(Object other)
    {
        if(!(other instanceof DesignationDTO)) return false;
        DesignationDTO designationDTO=(DesignationDTO)other;
        return this.code==designationDTO.code;
    }

    public int compareTo(DesignationDTOInterface designationDTO)
    {
        return this.code-designationDTO.getCode();
    }

    public int hashCode()
    {
        return code;
    }

}
