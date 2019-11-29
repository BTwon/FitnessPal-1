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

@WebServlet("/AddWorkoutServlet")
@MultipartConfig
public class AddWorkoutServlet extends HttpServlet {
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
	    
	    if (!video.matches("^(https?\\:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/.+$")) {
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("alert('Please enter a youtube url');");
	    	out.println("location='add-workout.html';");
	    	out.println("</script>");
	    	return;
	    }
	    
	    HttpSession session = request.getSession();
	    String user = session.getAttribute("username").toString();
	    
	    String equipment_str = "";
	    String muscles_str = "";
	    
	    System.out.println(equipment);
	    
	    if(equipment != null)
	    	equipment_str = String.join(",", equipment);
	    if(muscles != null)
	    	muscles_str = String.join(",", muscles);
	    
	    
	    InputStream inputStream= null;
	    
	    Part filePart = request.getPart("image");
        if (filePart != null) {
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        } else {
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("alert('Please upload an image');");
	    	out.println("location='add-workout.html';");
	    	out.println("</script>");
	    	return;
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	    String query = "INSERT INTO workout(name, skill_level, type, "
	    	+ "equipment, muscles, instructions, image_content, video, user) VALUES "
	    	+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, skill_level);
	    pstmt.setString(3, type);
	    pstmt.setString(4, equipment_str);
	    pstmt.setString(5, muscles_str);
	    pstmt.setString(6, instructions);
	    pstmt.setBlob(7, inputStream);
	    pstmt.setString(8, video);
	    pstmt.setString(9, user);
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='add-workout.jsp';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Workout Uploaded Successfully');");
	  out.println("location='index.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

