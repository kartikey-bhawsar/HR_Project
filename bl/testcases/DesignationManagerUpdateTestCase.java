import java.util.List;

import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.DesignationManagerInterface;
import com.thinking.machines.hr.bl.interfaces.pojo.DesignationInterface;
import com.thinking.machines.hr.bl.managers.DesignationManager;
import com.thinking.machines.hr.bl.pojo.Designation;

public class DesignationManagerUpdateTestCase {
    public static void main(String[] args) {
        try {
            DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
            DesignationInterface designation = new Designation();
            designation.setCode(3);
            designation.setTitle("Technician");
            designationManager.updateDesignation(designation);
            System.out.println("Designation updated successfully");
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
