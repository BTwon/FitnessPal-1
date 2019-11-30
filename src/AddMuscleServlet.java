import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.sql.*;

@WebServlet("/AddMuscleServlet")
@MultipartConfig
public class AddMuscleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
	    int muscle_group_id = Integer.parseInt(request.getParameter("muscle_group"));
	    
	    HttpSession session = request.getSession();
	    String user = session.getAttribute("username").toString();

	    InputStream inputStream= null;
	    
	    Part filePart = request.getPart("image");
        if (filePart != null) {
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        } else {
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("alert('Please upload an image');");
	    	out.println("location='add-muscle.html';");
	    	out.println("</script>");
	    	return;
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	    String query = "INSERT INTO muscle(name, description, muscle_group_id, image_content, user) VALUES "
	    	+ "(?, ?, ?, ?, ?)";
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, description);
	    pstmt.setInt(3, muscle_group_id);
	    pstmt.setBlob(4, inputStream);
	    pstmt.setString(5, user);
	    
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='add-muscle.jsp';");
		  out.println("</script>");
		  return;
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Muscle Uploaded Successfully');");
	  out.println("location='browse-all-muscles.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

