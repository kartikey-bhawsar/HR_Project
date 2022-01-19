import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesignationDeleteTestCase {
    public static void main(String gg[]) {
        try {
            DesignationDAOInterface dao = new DesignationDAO();
            dao.delete(Integer.parseInt(gg[0]));
            System.out.println("Designation with code " + gg[0] + " successfully deleted");
        } catch (DAOException dao) {
            System.out.println(dao.getMessage());
        }
    }
}
