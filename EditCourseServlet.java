import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/editcourse")
public class EditCourseServlet extends HttpServlet {
	private final static String query = "SELECT cid, cname, rating, duration FROM course WHERE cid = ?";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String cidToEdit = req.getParameter("cid_to_edit");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, cidToEdit);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String cid = rs.getString("cid");
                String cname = rs.getString("cname");
                String rating = rs.getString("rating");
                String duration = rs.getString("duration");

                // Display the form with pre-filled values for editing
                pw.println("<form action='updatecourse' method='post'>");
                pw.println("<h2>Edit Course</h2>");
                pw.println("<input type='hidden' name='c_id' value='" + cid + "'>");
                pw.println("Course ID: " + cid + "<br>");
                pw.println("Course Name: <input type='text' name='c_name' value='" + cname + "' required><br>");
                pw.println("Review Rating: <input type='text' name='rating' value='" + rating + "' required><br>");
                pw.println("Duration: <input type='text' name='duration' value='" + duration + "' required><br>");
                pw.println("<button type='submit'>Update</button>");
                pw.println("</form>");
            } else {
                pw.println("Course not found for editing.");
            }
        } catch (SQLException se) {
            pw.println("<h2>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.close();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
