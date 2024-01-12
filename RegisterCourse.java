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
@WebServlet("/enter")
public class RegisterCourse extends HttpServlet {
	private final static String query="insert into course(cid,cname,rating,duration) values(?,?,?,?)";
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String cid=req.getParameter("c_id");
		String cname=req.getParameter("c_name");
		String rating=req.getParameter("rating");
		String duration=req.getParameter("duration");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///mydb2","root","");
				PreparedStatement ps=con.prepareStatement(query);){
			ps.setString(1,cid);
			ps.setString(2, cname);
			ps.setString(3,rating);
			ps.setString(4,duration);
			int count=ps.executeUpdate();
			if(count==1)
			{
				pw.println("Record Registered Successfully");
			}
			else
			{
				pw.println("Record not Registered");
			}
		}
		catch(SQLException se)
		{
			pw.println(se.getMessage());
			se.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		pw.close();
	}
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		doGet(req,res);
	}
}
