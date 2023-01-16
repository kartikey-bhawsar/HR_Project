package com.thinking.machines.hr.bl.exceptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BLException extends Exception{
    Map<String,String> exceptions;
    String genericException;
    public BLException()
    {
        genericException=null;
        exceptions=new HashMap<>();
    }
    public void setGenericException(String exception)
    {
        this.genericException=exception;
    }
    public String getGenericException()
    {
        if(genericException==null) return "";
        return this.genericException;
    }
    public void addException(String property,String exception)
    {
        this.exceptions.put(property,exception);
    }
    public String getException(String property)
    {
        return this.exceptions.get(property);
    }
    public void removeException(String property)
    {
        this.exceptions.remove(property);
    }
    public int getExceptionCount()
    {
        if(genericException!=null) return this.exceptions.size()+1;
        return this.exceptions.size();
    }
    public boolean hasException(String property)
    {
        return this.exceptions.containsKey(property);
    }
    public boolean hasGenericException()
    {
        return this.genericException!=null;
    }
    public boolean hasExceptions()
    {
        return this.exceptions.size()>0;
    }
    public List<String> getProperties()
    {
        return this.exceptions.keySet().stream().collect(Collectors.toList());
    }
    public String getMessage()
    {
        if(genericException==null) return "";
        return genericException; 
    }
}
