package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddStudent
 */
@WebServlet("/AddStudent")
public class AddStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.sendRedirect("addStudents.html");
		try {
			PrintWriter out = response.getWriter();

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management", "root",
					"Root@1721");

			PreparedStatement ps = con
					.prepareStatement("insert into Student (name,age,classname,address) values (?,?,?,?)");

			ps.setString(1, request.getParameter("name"));
			ps.setInt(2, Integer.parseInt(request.getParameter("age")));
			ps.setString(3, request.getParameter("classname"));
			ps.setString(4, request.getParameter("address"));

			int result = ps.executeUpdate();

			if (result > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("addStudents.html");
				rd.forward(request, response);

				System.out.println("SuccessFully Added!...");
			} else {
				out.println("<font color='red' size='18'>Insertion Failed!<br>");
			}

		} catch (Exception e)

		{
			System.out.println(e);
		}

	}

}
