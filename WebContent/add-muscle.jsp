<%
boolean isLoggedIn = false;

if(session.getAttribute("username") != null){
	isLoggedIn = true;
} else {
	response.sendRedirect("login.jsp");
}
%>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="/your-path-to-fontawesome/css/fontawesome.css" rel="stylesheet">
<link rel="stylesheet" href="stylesheet.css">
</head>
<body>
<script src="https://kit.fontawesome.com/2588551113.js" crossorigin="anonymous"></script>
<div class="sidenav">
  <a href="index.jsp"><i class="fa fa-fw fa-home"></i> Home</a>
  <button class="dropdown-btn"><i class="fas fa-heart"></i> Workouts<i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
    <a href="cardio.jsp"><i class="fas fa-running"></i> Cardio</a>
    <a href="weight-lifting.jsp"><i class="fas fa-dumbbell"></i> Weight Lifting</a>
    <a href="browse-all-workouts.jsp"><i class="fas fa-search"></i> Browse All Workouts</a>
    <% if(isLoggedIn) out.print("<a href=\"add-workout.jsp\"><i class=\"fas fa-plus\"></i> Add Workout</a>"); %>
    <% if(isLoggedIn) out.print("<a href=\"edit-all-workouts.jsp\"><i class=\"fas fa-edit\"></i> Edit Your Workouts</a>"); %>
  </div>
  <button class="dropdown-btn"><i class="fas fa-dumbbell"></i> Equipment<i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
    <a href="browse-all-equipment.jsp"><i class="fas fa-search"></i> Browse Equipment</a>
    <% if(isLoggedIn) out.print("<a href=\"add-equipment.jsp\"><i class=\"fas fa-plus\"></i> Add Equipment</a>"); %>
    <% if(isLoggedIn) out.print("<a href=\"edit-all-equipment.jsp\"><i class=\"fas fa-edit\"></i> Edit Your Equipment</a>"); %>
  </div>
  <button class="dropdown-btn"><i class="fas fa-swimmer"></i> Muscle Groups<i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
    <a href="browse-all-muscle-groups.jsp"><i class="fas fa-search"></i> Browse Muscle Groups</a>
    <% if(isLoggedIn) out.print("<a href=\"add-muscle-group.jsp\"><i class=\"fas fa-plus\"></i> Add Muscle Group</a>"); %>
    <% if(isLoggedIn) out.print("<a href=\"edit-all-muscle-groups.jsp\"><i class=\"fas fa-edit\"></i> Edit Your Muscle Groups</a>"); %>
  </div>
  <button class="dropdown-btn"><i class="fas fa-dna"></i> Muscles<i class="fa fa-caret-down"></i>
  </button>
  <div class="dropdown-container">
    <a href="browse-all-muscles.jsp"><i class="fas fa-search"></i> Browse Muscles</a>
    <% if(isLoggedIn) out.print("<a href=\"add-muscle.jsp\"><i class=\"fas fa-plus\"></i> Add Muscle</a>"); %>
    <% if(isLoggedIn) out.print("<a href=\"edit-all-muscles.jsp\"><i class=\"fas fa-edit\"></i> Edit Your Muscles</a>"); %>
  </div>
  <% if(!isLoggedIn) out.print("<a href=\"login.jsp\"><i class=\"fa fa-user\"></i> Login</a>" +
  							   "<a href=\"register.jsp\"><i class=\"fa fa-user\"></i> Register</a>"); %>
  <% if(isLoggedIn) out.print("<a href=\"LogoutServlet\"> Logout</a>"); %>

</div>
<div class="header"><h1 class="logo"><span style="color:lightblue">Fitness</span>Pal</h1>
</div>

<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>

<% 
	String dbURL = "jdbc:mysql://localhost/MyFitnessPal";
    Connection connection = DriverManager.getConnection(dbURL, "root", "BACHlover1234");
    String query = "SELECT * FROM muscle_group";
    PreparedStatement pstmt = connection.prepareStatement(query);   
    
    ResultSet rs = pstmt.executeQuery() ;    
%>


<div class="main">
	<form action="search-results.jsp">
	<div class="search-box">
	  <input class="search-txt" name="search_query" placeholder="Type to search">
	  <button class ="search-btn">
	  <i class="fas fa-search"></i>
	  </button>
	</div>
	</form>
	<hr>
	<h1><i class="fas fa-dna"></i> Add Muscle</h1>
	<p> To add a muscle to the database, please fill out the following fields:</p>
  
  	<form id="add-muscle" action="AddMuscleServlet" method="post" enctype="multipart/form-data">
  		<section class="first">
          	<fieldset class="first_fieldset">
              	<legend>Muscle</legend>
              	<label> Muscle Name: </label><br>
              	<input type="text" name = "name" size="25"><br>
              	
              	<label> Description: </label>
  				<textarea name="description"></textarea><br> 
              	
              	<label> Muscle Group To Which the Muscle Belongs: </label>
              	<select name="muscle_group">
              		<% 
					while(rs.next()){
              			out.println("<option value=\"" + rs.getString("id") + "\">" + rs.getString("name") + "</option>");
              		}
              		%>
                </select><br>
            
                <br><label> Muscle Image: </label><br>
                <input type="file" name="image"><br>
                
  			</fieldset>
  			</section>
      	 <button type="submit" >Submit</button>
  	 </form>            
  
  
</div>





</div>
<footer>
  <div class="footer">
    <div class="footer-content">
      <div class="footer-section about">
        <h1 class="logo"><span style="color:lightblue">Fitness</span>Pal</h1>
        <p>FitnessPal is a fitness website conceived for the purpose improving ones health body
        by showing the best workouts to stay fit and strong.</p>
        <div class="contact">
          <span><i class="fas fa-envelope"></i> &nbsp; infor@fitnesspal.com</span>
          <span><i class="fas fa-phone"></i> &nbsp; 123-456-7890</span>
        </div>
        <div class="socials">
          <a href="https://twitter.com"><i class="fab fa-twitter"></i></a>
          <a href="https://www.instagram.com"><i class="fab fa-instagram"></i></a>
          <a href="https://www.facebook.com"><i class="fab fa-facebook"></i></a>
          <a href="https://www.youtube.com"><i class="fab fa-youtube"></i></a>
        </div>
      </div>
      <div class="footer-section links">
        <h2>Links</h2>
        <br>
        <ul>
          <a href="#"><li>Events</li></a>
          <a href="#"><li>Team</li></a>
          <a href="#"><li>Terms and Conditions</li></a>
        </ul>
      </div>
    </div>
    <div class="footer-bottom">
      &copy; FitnessPal.com | Designed by Bum Kwon
    </div>
  </div>
</footer>
<script>
/* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
function calc(){
  var n1, n2;
  n1 = document.getElementsById("n1").value;
  n2 = document.getElementById("n2").value;
  var workout = document.getElementById("opr").value;
  if(workout == '0'){
    var calories = 5;
      var x = n1*n2;
      document.getElementById("results").innerHTML = x;
  }
}


var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
  dropdown[i].addEventListener("click", function() {
  this.classList.toggle("active");
  var dropdownContent = this.nextElementSibling;
  if (dropdownContent.style.display === "block") {
  dropdownContent.style.display = "none";
  } else {
  dropdownContent.style.display = "block";
  }
  });
}
</script>
</body>
</html>
