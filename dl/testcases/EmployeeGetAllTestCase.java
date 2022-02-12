import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;
import com.thinking.machines.hr.dl.dao.*;
import java.util.Set;
import java.util.TreeSet;

public class EmployeeGetAllTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAOInterface dao = new EmployeeDAO();
            Set<EmployeeDTOInterface> st=new TreeSet<>();
            st = dao.getAll();
            for(EmployeeDTOInterface e:st)
            {
                System.out.println(e.getEmployeeId());
                System.out.println(e.getName());
                System.out.println(e.getDesignationCode());
                System.out.println(e.getDateOfBirth());
                System.out.println(e.getGender());
                System.out.println(e.isIndian());
                System.out.println(e.getBasicSalary().toPlainString());
                System.out.println(e.getPANNumber());
                System.out.println(e.getAadharNumber());
                System.out.println("*************************************************");
            }
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
