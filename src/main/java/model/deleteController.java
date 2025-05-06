package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deleteController
 */
@WebServlet("/deleteController")
public class deleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deleteController() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		try {

			PrintWriter out = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee_Management", "root",
					"Root@1721");

			String updateQuery = "delete from Student where id = ?";
			PreparedStatement ps = con.prepareStatement(updateQuery);

			ps.setInt(1, Integer.parseInt(request.getParameter("id")));

			int result = ps.executeUpdate();

			if (result > 0) {
				System.out.println("Succefully deleted");
			} else {
				System.out.println("Invali Id");
			}

		} catch (Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
		}

		response.sendRedirect("DeleteStudents.html");
	}
}
