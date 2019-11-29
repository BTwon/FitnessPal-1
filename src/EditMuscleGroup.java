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

@WebServlet("/EditMuscleGroup")
@MultipartConfig
public class EditMuscleGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String name = request.getParameter("name");
	    String description = request.getParameter("description");
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
            query = "UPDATE muscle_group SET name = ?, description = ?, user = ?, image_content = ? WHERE id = ?";
        } else {
        	query = "UPDATE muscle_group SET name = ?, description = ?, user = ? WHERE id = ?";
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, description);
	    pstmt.setString(3, user);
	    if(hasImage) {
	    	pstmt.setBlob(4, inputStream);
	    	pstmt.setInt(5, id);
	    } else {
	    	pstmt.setInt(4, id);
	    }
    
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(SQLException e) {
		  e.printStackTrace();
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Please enter all required fields');");
		  out.println("location='add-muscle-group.jsp';");
		  out.println("</script>");
		  return;
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='add-muscle-groupt.jsp';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Muscle Group Updated Successfully');");
	  out.println("location='index.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

