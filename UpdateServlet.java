import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatecourse")
public class UpdateServlet extends HttpServlet {
    private final static String query = "UPDATE course SET cname=?, rating=?, duration=? WHERE cid=?";

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String cid = req.getParameter("c_id");
        String cname = req.getParameter("c_name");
        String rating = req.getParameter("rating");
        String duration = req.getParameter("duration");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, cname);
            ps.setString(2, rating);
            ps.setString(3, duration);
            ps.setString(4, cid);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("Record Updated Successfully");
            } else {
                pw.println("Record not Updated");
            }
        } catch (SQLException se) {
            pw.println("<h2>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pw.close();
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}
