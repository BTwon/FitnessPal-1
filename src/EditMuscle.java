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

@WebServlet("/EditMuscle")
@MultipartConfig
public class EditMuscle extends HttpServlet {
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
	    int id = Integer.parseInt(request.getParameter("id"));
	    
	    HttpSession session = request.getSession();
	    String user = session.getAttribute("username").toString();
	    
	    if(name.isEmpty() || description.isEmpty()) {
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("alert(\"Please enter all required fields\");");
	    	out.println("location='edit-muscle.jsp?id="+id+"';");
	    	out.println("</script>");
	    	return;
	    }
	    
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
            query = "UPDATE muscle SET name = ?, description = ?, user = ?, muscle_group_id = ?, image_content = ? WHERE id = ?";
        } else {
        	query = "UPDATE muscle SET name = ?, description = ?, user = ?, muscle_group_id = ? WHERE id = ?";
        }
        	    
	    String dbURL = "jdbc:mysql://localhost/MyFitnessPal?allowMultiQueries=true";
	    
	  try {
	    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
	    PreparedStatement pstmt = connection.prepareStatement( query );
	    pstmt.setString(1, name);
	    pstmt.setString(2, description);
	    pstmt.setString(3, user);
	    pstmt.setInt(4, muscle_group_id);
	    if(hasImage) {
	    	pstmt.setBlob(5, inputStream);
	    	pstmt.setInt(6, id);
	    } else {
	    	pstmt.setInt(5, id);
	    }

    
	    pstmt.executeUpdate( );
	    
	    connection.close();
	  }
	  catch(Exception e) {
		  e.printStackTrace();
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert('Something went wrong...');");
		  out.println("location='edit-muscle.jsp?id=" + Integer.toString(id) + "';");
		  out.println("</script>");
	  }
	  
	  out.println("<script type=\"text/javascript\">");
	  out.println("alert('Muscle Updated Successfully');");
	  out.println("location='edit-all-muscles.jsp';");
	  out.println("</script>");
	  
	  
	}
	
}

