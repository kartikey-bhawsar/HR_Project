import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;

public class EmployeeGetCountTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAOInterface dao = new EmployeeDAO();
            int c = dao.getCount();
            System.out.println(c);
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
