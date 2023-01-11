import com.thinking.machines.hr.dl.dao.EmployeeDAO;

public class EmployeeEmployeeIdExistsTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAO dao=new EmployeeDAO();
            System.out.println(dao.employeeIdExists("adsf0965kf"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
