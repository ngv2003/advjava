<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Management</title>
</head>
<body>
    <h1>COURSE MANAGEMENT</h1>

    <!-- Insert Course Section -->
    <h2>REGISTER COURSE</h2>
    <%
        String cid = request.getParameter("c_id");
        String cname = request.getParameter("c_name");
        String rating = request.getParameter("rating");
        String duration = request.getParameter("duration");

        if (cid != null && cname != null && rating != null && duration != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
                String insertQuery = "insert into course(cid, cname, rating, duration) values(?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, cid);
                ps.setString(2, cname);
                ps.setString(3, rating);
                ps.setString(4, duration);

                int count = ps.executeUpdate();

                if (count == 1) {
                    out.println("Record Registered Successfully");
                } else {
                    out.println("Record not Registered");
                }

                ps.close();
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    %>

    <form action="" method="post">
        <h3>course id</h3>
        <input type="text" name="c_id" required>
        <h3>course name</h3>
        <input type="text" name="c_name" required>
        <h3>review rating</h3>
        <input type="text" name="rating" required>
        <h3>duration</h3>
        <input type="text" name="duration" required>
        <button type="submit">Register</button>
    </form>

    <!-- Delete Course Section -->
    <h2>DELETE COURSE</h2>
    <%
        String cidToDelete = request.getParameter("cid_to_delete");

        if (cidToDelete != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
                String deleteQuery = "DELETE FROM course WHERE cid = ?";
                PreparedStatement ps = con.prepareStatement(deleteQuery);
                ps.setString(1, cidToDelete);

                int count = ps.executeUpdate();

                if (count == 1) {
                    out.println("Record Deleted Successfully");
                } else {
                    out.println("Record not Found or Not Deleted");
                }

                ps.close();
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    %>

    <form action="" method="post">
        <h3>Enter Course ID to Delete</h3>
        <input type="text" name="cid_to_delete" required>
        <button type="submit">Delete</button>
    </form>

    <!-- Edit Course Section -->
    <h2>EDIT COURSE</h2>
    <%
        String cidToEdit = request.getParameter("cid_to_edit");
        String editedCname = request.getParameter("edited_cname");
        String editedRating = request.getParameter("edited_rating");
        String editedDuration = request.getParameter("edited_duration");

        if (cidToEdit != null && editedCname != null && editedRating != null && editedDuration != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql:///mydb2", "root", "");
                String editQuery = "UPDATE course SET cname=?, rating=?, duration=? WHERE cid=?";
                PreparedStatement ps = con.prepareStatement(editQuery);
                ps.setString(1, editedCname);
                ps.setString(2, editedRating);
                ps.setString(3, editedDuration);
                ps.setString(4, cidToEdit);

                int count = ps.executeUpdate();

                if (count == 1) {
                    out.println("Record Updated Successfully");
                } else {
                    out.println("Record not Found or Not Updated");
                }

                ps.close();
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    %>

    <form action="" method="post">
        <h3>Enter Course ID to Edit</h3>
        <input type="text" name="cid_to_edit" required>
        <h3>Edited course name</h3>
        <input type="text" name="edited_cname" required>
        <h3>Edited review rating</h3>
        <input type="text" name="edited_rating" required>
        <h3>Edited duration</h3>
        <input type="text" name="edited_duration" required>
        <button type="submit">Edit</button>
    </form>
</body>
</html>
