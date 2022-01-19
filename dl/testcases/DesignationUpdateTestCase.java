import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesignationUpdateTestCase {
    public static void main(String gg[]) {
        try {
            DesignationDTOInterface dto = new DesignationDTO();
            dto.setCode(Integer.parseInt(gg[0]));
            dto.setTitle(gg[1]);
            DesignationDAOInterface dao = new DesignationDAO();
            dao.update(dto);
            System.out.println("Designation " + gg[1] + " updated with code as "+dto.getCode() );
        } catch (DAOException dao) {
            System.out.println(dao.getMessage());
        }
    }
}
