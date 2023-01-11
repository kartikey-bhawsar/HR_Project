import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;

public class EmployeeGetCountByDesignationCodeTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAOInterface dao = new EmployeeDAO();
            int c = dao.getCountByEmployeeDesignation(3);
            System.out.println(c);
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
