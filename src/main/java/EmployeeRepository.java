import java.sql.*;

public class EmployeeRepository {

    public void doSomething(int salary) throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM emp where sal > " + salary;

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.print(rs.getString("ENAME") + " ");
                System.out.println(rs.getInt(1));
            }
        }
    }

    public void doSomething2(int salary) throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT * FROM emp where sal > ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, salary);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getString("ENAME") + " ");
                System.out.print(rs.getInt(1) + " ");
                System.out.println(rs.getInt(6));
            }
        }
    }


    public void printEmployees() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT ENAME, EMPNO FROM EMP";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println("Employee number: " + rs.getInt("EMPNO") + ", employee name: " + rs.getString("ENAME"));
            }
        }
    }

    public void printEmployees2() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT ENAME, EMPNO FROM EMP where ENAME like 's%'";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println("Employee number: " + rs.getInt("EMPNO") + ", employee name: " + rs.getString("ENAME"));
            }
        }
    }

    public void printEmployeesSum() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT sum(sal) as total_salary FROM EMP";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println("Samlet løn for alle medarbejderer: " + rs.getInt("total_salary"));
            }
        }
    }

    public void printHighestPaidEmployee() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT ename, sal FROM EMP ORDER BY sal DESC limit 1";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                System.out.println("Highest paid employee: " + rs.getString("ename") + ", salary: " + rs.getInt("sal"));
            }
        }
    }

    public void printEmployeesHigherPaidThanAvg() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT ename, sal FROM EMP WHERE sal > (SELECT avg(sal) FROM emp)";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println("Employee name: " + rs.getString("ename") + ", salary: " + rs.getInt("sal"));
            }
        }
    }

    public void printEmployeeByMgrName(String mngr) throws SQLException {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT ename, mgr FROM emp where mgr = (SELECT empno from emp where ename = ?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, mngr);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Employee name: " + rs.getString("ename") + ", manager number: " + rs.getInt("mgr"));
            }
        }
    }


    public void printDepartmentWithMoreThan5Workers() throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "root", "joakim-224/kea")) {

            String SQL = "SELECT d.dname as d_name FROM dept d INNER JOIN emp e ON d.deptno = e.deptno GROUP BY d.dname having count(*) > 5";
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                System.out.println("Department with more than 5 workers: " + rs.getString("d_name"));
            }
        }
    }
}
