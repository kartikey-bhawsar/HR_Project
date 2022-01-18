import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.dto.DesignationDTO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;
import com.thinking.machines.hr.dl.interfaces.dto.DesignationDTOInterface;

public class DesgnationAddTestCase {
    public static void main(String gg[]){
    try{
        DesignationDTOInterface dto=new DesignationDTO();
        dto.setTitle(gg[0]);
        DesignationDAOInterface dao=new DesignationDAO();
        dao.add(dto);
        System.out.println("Designation "+gg[0]+ " succussfully added");
    } catch(DAOException dao)
    {
        System.out.println(dao.getMessage());
    }
    }
}
