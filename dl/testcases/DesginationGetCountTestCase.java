import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;

public class DesginationGetCountTestCase {
    public static void main(String args[]) {
        try {
            DesignationDAOInterface dao = new DesignationDAO();
            int c = dao.getCount();
            System.out.println(c);
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
