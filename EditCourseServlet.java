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
	private final static String query = "UPDATE course SET cname=?, rating=?, duration=? WHERE cid=?";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String cidToEdit = req.getParameter("cid_to_edit");
        String editedCname = req.getParameter("edited_cname");
        String editedRating = req.getParameter("edited_rating");
        String editedDuration = req.getParameter("edited_duration");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
             PreparedStatement ps = con.prepareStatement(query);) {
        	ps.setString(1, editedCname);
            ps.setString(2, editedRating);
            ps.setString(3, editedDuration);
            ps.setString(4, cidToEdit);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("Record Updated Successfully");
            } else {
                pw.println("Record not Found or Not Updated");
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
