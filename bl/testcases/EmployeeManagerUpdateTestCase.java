package bl.testcases;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.EmployeeManagerInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.EmployeeInterface;
import com.thinking.machines.hr.bl.managers.EmployeeManager;
import com.thinking.machines.hr.bl.pojo.Designation;
import com.thinking.machines.hr.bl.pojo.Employee;

public class EmployeeManagerUpdateTestCase {
    public static void main(String[] args) {
        try {
            EmployeeInterface e=new Employee();
            e.setEmployeeId("1000003");
            e.setAadharNumber("A3234");
            e.setBasicSalary(new BigDecimal("435233"));
            System.out.println("dgsdgdsgdf");
            try{
                e.setDateOfBirth(new SimpleDateFormat("dd/MM/yyyy").parse("12/05/1993"));
            }catch(ParseException pe)
            {
                System.out.println(pe.getMessage());
            }
            DesignationInterface designation=new Designation();
            designation.setCode(2);
            e.setDesignation(designation);
            e.setGender('M');
            e.setIsIndian(true);
            e.setName("Fameer Gupta");
            e.setPANNumber("U43w1");
            EmployeeManagerInterface empManager=EmployeeManager.getEmployeeManager();
            empManager.updateEmployee(e);
            System.out.println("Employee successfully updated with employee id: "+e.getEmployeeId());
        } catch (BLException e) {
            if(e.hasGenericException()) System.out.println(e.getGenericException());
            e.getProperties().stream().forEach((exception)->System.out.println(e.getException(exception)));
        }
    }
}
