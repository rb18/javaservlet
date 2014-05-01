

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
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

public static Connection conn=null;
public static Statement stmt=null;

public class DatabaseAccess extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/Junior";

		
		final String USER = "root";
		final String PASS = "";
                
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Database Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 "
				+ "transitional//en\">\n";
		out.println(docType + "<html>\n" + "<head><title>" + title
				+ "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n"
				+ "<h1 align=\"center\">" + title + "</h1>\n");
		try {
			
                        
			Class.forName("com.mysql.jdbc.Driver");

			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT no, ad, soyad, not FROM Student";
			ResultSet rs = stmt.executeQuery(sql);

			
			while (rs.next()) {
				
				int no = rs.getInt("no");
				
				String ad = rs.getString("Ad");
				String soyad = rs.getString("Soyad");
                                int not = rs.getInt("not");
				
				out.println("ID: " + no + "<br>");
				
				out.println(", First: " + ad + "<br>");
				out.println(", Last: " + soyad + "<br>");
                                out.println(", Age: " + not + "<br>");
			}
			out.println("</body></html>");

			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			
			se.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			try {
                           
				if (stmt != null)
					stmt.close();
                                
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
                                
                                
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} 
	}
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
