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
@WebServlet("/showcourses")
public class ShowCourse extends HttpServlet {
	private final static String query="select cid,cname from course where duration>100";
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///mydb2","root","");
				PreparedStatement ps=con.prepareStatement(query);){
			ResultSet rs=ps.executeQuery();
			String f1,f2,f3,f4;
			while(rs.next())
			{
				f1=rs.getString(1);
				f2=rs.getString(2);
				pw.print(f1+"       "+f2+"        "+"<br>");
			}
		}
		catch(SQLException se)
		{
			pw.println("<h2>"+se.getMessage()+"</h2>");
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
