import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.EmployeeManagerInterface;
import com.thinking.machines.hr.bl.managers.EmployeeManager;
import com.thinking.machines.hr.dl.exceptions.DAOException;

public class EmployeeManagerRemoveTestCase {
    public static void main(String[] args) {
        try {
            EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
            employeeManager.removeEmployee("1000002");
            System.out.println("Employee successfully deleted");
        } catch (BLException e) {
            if(e.hasGenericException()) System.out.println(e.getGenericException());
            e.getProperties().stream().forEach((exception)->System.out.println(e.getException(exception)));
        }
    }
}
