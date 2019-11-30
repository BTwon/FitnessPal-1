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

@WebServlet("/EditWorkout")
@MultipartConfig
public class EditWorkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String name = request.getParameter("name");
	    String skill_level = request.getParameter("skill_level");
	    String type = request.getParameter("type");
	    String [] equipment = request.getParameterValues("equipment");
	    String [] muscles = request.getParameterValues("muscles");
	    String instructions = request.getParameter("instructions");
	    String video = request.getParameter("video");
	    
	    String equipment_str = "";
	    String muscles_str = "";
	    
	    System.out.println(equipment);
	    
	    if(equipment != null)
	    	equipment_str = String.join(",", equipment);
	    if(muscles != null)
	    	muscles_str = String.join(",", muscles);

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
            query = "UPDATE workout SET name = ?, skill_level = ?, type = ?, equipment = ?, " + 
                    "muscles = ?, instructions = ?, video = ?, image_content = ? WHERE id = ?";
        } else {
            query = "UPDATE workout SET name = ?, skill_level = ?, type = ?, equipment = ?, " + 
                    "muscles = ?, instructions = ?, video = ? WHERE id = ?";        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, skill_level);
	    pstmt.setString(3, type);
	    pstmt.setString(4, equipment_str);
	    pstmt.setString(5, muscles_str);
	    pstmt.setString(6, instructions);
	    pstmt.setString(7, video);
	    
	    if(hasImage) {
	    	pstmt.setBlob(8, inputStream);
	    	pstmt.setInt(9, id);
	    } else {
	    	pstmt.setInt(8, id);
	    }
    
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='edit-workout.jsp?id=" + Integer.toString(id) + "';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Workout Updated Successfully');");
	  out.println("location='edit-all-workouts.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

