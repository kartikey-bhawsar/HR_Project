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

public class EmployeeDeleteTestCase {
    public static void main(String[] args) {
        try{
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Employee Id");
        String employeeId=sc.nextLine();
        EmployeeDAOInterface employeeDAO=new EmployeeDAO();
        employeeDAO.delete(employeeId);
        sc.close();
        System.out.println("Employee successfully deleted");
        }catch(DAOException daoe)
        {
            System.out.println("Error "+daoe.getMessage());
        }
    }
}
