//Database Table Structure:
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    date DATE,
    status VARCHAR(10)
);

//JSP Page (studentPortal.jsp):
<form action="AttendanceServlet" method="post">
    Student Name: <input type="text" name="student_name"><br>
    Date: <input type="date" name="date"><br>
    Status: <select name="status">
        <option value="Present">Present</option>
        <option value="Absent">Absent</option>
    </select><br>
    <input type="submit" value="Submit">
</form>

//Servlet Code:
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("student_name");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "root", "password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO attendance (student_name, date, status) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
            con.close();
            response.getWriter().println("Attendance recorded successfully.");
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
