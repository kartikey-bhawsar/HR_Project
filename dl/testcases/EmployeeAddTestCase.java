import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.thinking.machines.hr.dl.dao.EmployeeDAO;
import com.thinking.machines.hr.dl.dto.EmployeeDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.EmployeeDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;

public class EmployeeAddTestCase {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Employee Name");
        String name=sc.nextLine();
        System.out.println("Enter Employee designation code");
        int designationCode=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Employee date of birth");
        Date dateOfBirth;
        try{
            dateOfBirth=new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
        System.out.println("Enter Employee gender (M/F)");
        char gender=sc.next().charAt(0);
        System.out.println("Is employee Indian");
        boolean isIndian=sc.nextBoolean();
        sc.nextLine();
        System.out.println("Enter Employee basic salary");
        BigDecimal basicSalary=new BigDecimal(sc.nextLine());
        System.out.println("Enter Employee PAN number");
        String panNumber=sc.nextLine();
        System.out.println("Enter Employee Aadhar number");
        String aadharNumber=sc.nextLine();
        EmployeeDTOInterface employeeDTO=new EmployeeDTO();
        employeeDTO.setName(name);
        employeeDTO.setDesignationCode(designationCode);
        employeeDTO.setAadharNumber(aadharNumber);
        employeeDTO.setBasicSalary(basicSalary);
        employeeDTO.setDateOfBirth(dateOfBirth);
        employeeDTO.setIsIndian(isIndian);
        employeeDTO.setPANNumber(panNumber);
        employeeDTO.setGender(gender);
        EmployeeDAOInterface employeeDAO=new EmployeeDAO();
        employeeDAO.add(employeeDTO);
        sc.close();
        System.out.println("Employee successfully added with id: "+employeeDTO.getEmployeeId());
        }catch(DAOException daoe)
        {
            System.out.println("Error "+daoe.getMessage());
        }catch(ParseException pe)
        {
            System.out.println(pe.getMessage());
        }
    }
}
