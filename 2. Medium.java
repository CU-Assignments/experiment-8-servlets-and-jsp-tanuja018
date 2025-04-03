//Database Table Structure:
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(50)
);

//Servlet Code:
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "password");
            String id = request.getParameter("id");

            PreparedStatement ps;
            if (id == null || id.isEmpty()) {
                ps = con.prepareStatement("SELECT * FROM employees");
            } else {
                ps = con.prepareStatement("SELECT * FROM employees WHERE id = ?");
                ps.setInt(1, Integer.parseInt(id));
            }

            ResultSet rs = ps.executeQuery();
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("department") + "</td></tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}

//HTML Form to Search Employee:
<form action="EmployeeServlet" method="get">
    Enter Employee ID: <input type="text" name="id">
    <input type="submit" value="Search">
</form>
