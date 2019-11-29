import java.io.*;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.sql.*;

@WebServlet("/EditEquipment")
@MultipartConfig
public class EditEquipment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
	    String workout_type = request.getParameter("workout_type");
	    int id = Integer.parseInt(request.getParameter("id"));
	    
	    
	    HttpSession session = request.getSession();
	    String user = session.getAttribute("username").toString();

	    if(user == null){
	    	response.sendRedirect("login.jsp");
	    }

	    InputStream inputStream= null;
	    
	    Part filePart = request.getPart("image");   
	    
	    boolean hasImage = false;
	    String query;
        if (filePart.getSize() > 0) {
            // obtains input stream of the upload file
        	inputStream = filePart.getInputStream();
            hasImage = true;
            query = "UPDATE equipment SET name = ?, description = ?, workout_type = ?, user = ?, image_content = ? WHERE id = ?";
        } else {
        	query = "UPDATE equipment SET name = ?, description = ?, workout_type = ?, user = ? WHERE id = ?";
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, description);
	    pstmt.setString(3, workout_type);
	    pstmt.setString(4, user);

	    if(hasImage) {
	    	pstmt.setBlob(5, inputStream);
	    	pstmt.setInt(6, id);
	    } else {
	    	pstmt.setInt(5, id);
	    }
    
	    System.out.println(pstmt);
	    
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='edit-equipment.jsp?id=" + id +  "';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Equipment Updated Successfully');");
	  out.println("location='index.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

