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

@WebServlet("/AddRoutineServlet")
@MultipartConfig
public class AddRoutineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    String name = request.getParameter("name");
	    
	    HttpSession session = request.getSession();
	    String user = session.getAttribute("username").toString();
	    
	    String workout_ids = "";
	    for (int i=1; i<=10; i++) {
	    	String workout_id = request.getParameter("workout" + i);
	    	if(workout_id != null) {
	    		workout_ids += workout_id + ",";
	    	}
	    }
	    
	    workout_ids = workout_ids.substring(0, workout_ids.length()-1);	    

	    String description = request.getParameter("description");
	    String intensity = request.getParameter("intensity");
	    
	    InputStream inputStream= null;
	    
	    Part filePart = request.getPart("image");
        if (filePart != null) {
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        } else {
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("alert('Please upload an image');");
	    	out.println("location='add-routine.jsp';");
	    	out.println("</script>");
	    	return;
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	    String query = "INSERT INTO routine(name, workout_ids, description, "
	    	+ "intensity, image_content, user) VALUES "
	    	+ "(?, ?, ?, ?, ?, ?)";
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, workout_ids);
	    pstmt.setString(3, description);
	    pstmt.setString(4, intensity);
	    pstmt.setBlob(5, inputStream);
	    pstmt.setString(6, user);
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='add-routine.jsp';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Workout Uploaded Successfully');");
	  out.println("location='index.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

