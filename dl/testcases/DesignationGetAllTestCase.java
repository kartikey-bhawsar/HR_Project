import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
public class DesignationGetAllTestCase
{
public static void main(String gg[])
{
try
{
    Set<DesignationDTOInterface> st;
    DesignationDAOInterface dao=new DesignationDAO();
    st=dao.getAll();
    for(DesignationDTOInterface it:st)
    {
        System.out.println(it.getCode()+" "+it.getTitle());
    }
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}