import com.thinking.machines.hr.dl.dao.EmployeeDAO;

public class EmployeeAadharNumberExistsTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAO dao=new EmployeeDAO();
            System.out.println(dao.aadharNumberExists("adsf09365kf"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
