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

public class EmployeeManagerAddTestCase {
    public static void main(String[] args) {
        try {
            EmployeeInterface e=new Employee();
            e.setAadharNumber("A1234");
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
            e.setName("Sameer Gupta");
            e.setPANNumber("U4321");
            EmployeeManagerInterface empManager=EmployeeManager.getEmployeeManager();
            empManager.addEmployee(e);
            System.out.println("Employee successfully added with employee id: "+e.getEmployeeId());
        } catch (BLException e) {
            if(e.hasGenericException()) System.out.println(e.getGenericException());
            e.getProperties().stream().forEach((exception)->System.out.println(e.getException(exception)));
        }
    }
}
