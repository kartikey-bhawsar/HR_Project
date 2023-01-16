package com.thinking.machines.hr.bl.interfaces.pojo;
import java.io.Serializable;

public interface DesignationInterface extends Serializable,Comparable<DesignationInterface>{
    public void setCode(int code);
    public int getCode();
    public void setTitle(String title);
    public String getTitle();
}
