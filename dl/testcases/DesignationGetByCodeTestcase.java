import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesignationGetByCodeTestcase {
    public static void main(String args[]) {
        try {
            DesignationDAOInterface dao = new DesignationDAO();
            DesignationDTOInterface dto = dao.getByCode(Integer.parseInt(args[0]));
            System.out.println(dto.getCode()+" "+dto.getTitle());
        } catch (DAOException daoException) {
            System.out.println(daoException.getMessage());
        }
    }
}
