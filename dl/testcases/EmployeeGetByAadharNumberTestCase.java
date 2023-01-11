import com.thinking.machines.hr.dl.dao.EmployeeDAO;
import com.thinking.machines.hr.dl.interfaces.dao.EmployeeDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;

public class EmployeeGetByAadharNumberTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAOInterface dao=new EmployeeDAO();
            EmployeeDTOInterface employee=dao.getByAadharNumber("adsf0965kf");
            System.out.println(employee);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
