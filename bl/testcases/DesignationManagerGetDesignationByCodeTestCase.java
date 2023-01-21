import java.util.List;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.DesignationManagerInterface;
import com.thinking.machines.hr.bl.managers.DesignationManager;

public class DesignationManagerGetDesignationByCodeTestCase {
    public static void main(String[] args) throws BLException{
        try {
            DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
            System.out.println(designationManager.getDesignationByCode(2).getTitle());
        } catch (BLException e) {
            if (e.hasGenericException()) {
                System.out.println(e.getGenericException());
            }
            List<String> properties = e.getProperties();
            for (String property : properties) {
                System.out.println(e.getException(property));
            }
        }
    }
}
