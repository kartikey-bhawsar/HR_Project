import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;

public class EmployeeIsDesignationAllotedTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAOInterface dao = new EmployeeDAO();
            boolean c = dao.isDesignationAlloted(5);
            System.out.println(c);
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
