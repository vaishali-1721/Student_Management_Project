package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateStudent
 */
@WebServlet("/UpdateStudent")
public class UpdateStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateStudent() {
		super();
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("updateStudents.html");

		try {

			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management", "root",
					"Root@1721");

			String updateQuery = "UPDATE Student SET name=?, age=?, classname=?, address=? WHERE id=?";
			PreparedStatement ps = con.prepareStatement(updateQuery);

			ps.setString(1, request.getParameter("name"));
			ps.setInt(2, Integer.parseInt(request.getParameter("age")));
			ps.setString(3, request.getParameter("classname"));
			ps.setString(4, request.getParameter("address"));
			ps.setInt(5, Integer.parseInt(request.getParameter("id")));

			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated > 0) {

				RequestDispatcher rd = request.getRequestDispatcher("updateStudents.html");
				rd.forward(request, response);
				System.out.println("Successfully Updated!");
			} else {
				out.println("<font color='red' size='18'>Update Failed! Student ID not found.<br>");
			}

			ps.close();
			con.close();
		} catch (Exception e) {

			System.out.println(e);

		}
	}
}
