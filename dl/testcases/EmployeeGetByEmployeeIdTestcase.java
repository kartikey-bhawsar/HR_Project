import com.thinking.machines.hr.dl.dao.EmployeeDAO;
import com.thinking.machines.hr.dl.interfaces.dto.EmployeeDTOInterface;

public class EmployeeGetByEmployeeIdTestcase {
    public static void main(String[] args) {
        try {
            EmployeeDAO dao=new EmployeeDAO();
            EmployeeDTOInterface employee=dao.getByEmployeeId("1000002");
            System.out.println(employee);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
