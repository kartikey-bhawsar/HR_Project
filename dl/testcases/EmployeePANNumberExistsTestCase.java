import com.thinking.machines.hr.dl.dao.EmployeeDAO;

public class EmployeePANNumberExistsTestCase {
    public static void main(String[] args) {
        try {
            EmployeeDAO dao=new EmployeeDAO();
            System.out.println(dao.panNumberExists("asdf2365hf"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
