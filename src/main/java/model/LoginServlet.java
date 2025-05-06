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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		// Get parameters from the login form
		String n = request.getParameter("txtName").trim();
		String p = request.getParameter("txtPwd").trim();

		// Basic validation
		if (n.isEmpty() || p.isEmpty()) {
			out.println("<font color='red' size='18'>Username or Password cannot be empty!<br>");
			return;
		}

		// Initialize database connection and query
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establish database connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management", "root", "Root@1721");

			// Prepare the SQL query with placeholders
			ps = con.prepareStatement("SELECT uname FROM login WHERE uname = ? AND password = ?");

			// Set values for placeholders
			ps.setString(1, n);
			ps.setString(2, p);

			// Execute the query
			rs = ps.executeQuery();

			// Check if the user exists
			if (rs.next()) {
				// If login successful, forward to welcome page
				RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
				rd.forward(request, response);
			} else {
				// If login failed, display an error message
				out.println("<font color='red' size='18'>Login Failed! Invalid Username or Password.<br>");
			}
		} catch (Exception e) {
			// Print the error details to the page for easier debugging
			e.printStackTrace();
			out.println("<font color='red' size='18'>An error occurred during login.<br>");
		} finally {
			// Close database resources
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
