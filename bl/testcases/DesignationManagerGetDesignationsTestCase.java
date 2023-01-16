import com.thinking.machines.hr.bl.exceptions.BLException;
import com.thinking.machines.hr.bl.interfaces.managers.DesignationManagerInterface;
import com.thinking.machines.hr.bl.managers.DesignationManager;

public class DesignationManagerGetDesignationsTestCase {
    public static void main(String[] args) {
        try {
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            designationManager.getDesignations().stream().forEach((designation)->System.out.println(designation.getCode()+" "+designation.getTitle()));
        } catch (BLException e) {
            if(e.hasGenericException()) System.out.println(e.getGenericException());
            if(e.hasExceptions()) System.out.println("More exceptions "+e.getExceptionCount());
        }
    }
}
