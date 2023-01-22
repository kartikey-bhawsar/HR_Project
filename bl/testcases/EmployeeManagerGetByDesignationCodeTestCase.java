import java.util.Set;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.EmployeeManagerInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.EmployeeInterface;
import com.thinking.machines.hr.bl.managers.EmployeeManager;

public class EmployeeManagerGetByDesignationCodeTestCase {
    public static void main(String[] args) {
        try{
        EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
        Set<EmployeeInterface> e=employeeManager.getEmployeesByDesignationCode(1);
        e.stream().forEach((emp)->System.out.println(emp));
        }catch(BLException e)
        {
            if(e.hasGenericException()) System.out.println(e.getGenericException());
            e.getProperties().stream().forEach((exception)->System.out.println(e.getException(exception)));
        }
    }
}
