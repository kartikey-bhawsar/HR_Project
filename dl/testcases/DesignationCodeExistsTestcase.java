import com.thinking.machines.hr.dl.dao.DesignationDAO;
import com.thinking.machines.hr.dl.exceptions.DAOException;
import com.thinking.machines.hr.dl.interfaces.dao.DesignationDAOInterface;

public class DesignationCodeExistsTestcase {
    public static void main(String[] args) {
        try{
        DesignationDAOInterface dao=new DesignationDAO();
        if(dao.titleExists(args[0])) System.out.println(args[0]+" designation exists");
        else System.out.println(args[0]+" title doesn't exists");
        }catch(DAOException daoException)
        {
            System.out.println(daoException.getMessage());
        }
    }
}
