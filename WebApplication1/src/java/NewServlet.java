import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author RidvanBal
 */
@WebServlet(urlPatterns = { "/NewServlet" })
public class NewServlet extends HttpServlet {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/Junior";
	private static final String USER = "root";
	private static final String PASS = "";

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
                
		return conn;
                
	}

	private String postStudentAsHtml(String studentName) {
		Connection conn = null;
		Statement stmt = null;
		StringBuffer buffer = new StringBuffer("");
               
		try {

			conn = createConnection();
			stmt = conn.createStatement();
                         
			String sql = "SELECT * FROM Student WHERE";
			if (studentName != null && !studentName.trim().isEmpty()) {
				sql += " ad = '" + studentName + "'";
			}
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				int no = rs.getInt("no");
				String ad = rs.getString("Ad");
				String soyad = rs.getString("Soyad");
				int not = rs.getInt("not");

				buffer.append("Numara: " + no + "<br>");
				buffer.append(", Ad: " + ad + "<br>");
				buffer.append(", Soyad: " + soyad + "<br>");
				buffer.append(", Not: " + not + "<br>");
			}

			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				if (stmt != null)
					stmt.close();

				if (conn != null)
					conn.close();
			} catch (Exception se2) {
				se2.printStackTrace();
			}
		}
		return buffer.toString();
	}

	private void processStudentSearchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String title = "Database Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n");
		String name = request.getParameter("name");
                String result = postStudentAsHtml(name);
		out.println(result);
		out.println("</body></html>");
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processStudentSearchRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}



