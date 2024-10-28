<%@page import="com.techtech.dto.ProductDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Product Page</title>
 	<meta charset="utf-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
	<h1>Product Page</h1>
	<header style="height: 30px; background-color: grey;">
	</header>
	<div class="container">
		<img style="height: 300px;" src="https://marketplace.canva.com/EAFZsgyNH6w/1/0/1600w/canva-yellow-simple-minimal-welcome-banner-instagram-post-JyZG7-05130.jpg">
		<div class="form-group" style="width:50%">
			<form action="addProduct" method="POST">
				<label><b>Name</b></label>
				<input type="text" class="form-control" name="name"/>
				
				<label><b>Price</b></label>
				<input type="text" class="form-control" name="price" style="width:200px;"/>
				
				<br/>
				<label><b>Category</b></label>
				<select class="form-control" name="category" style="width:200px;">
					<option>Mobile</option>
					<option>Laptop</option>
					<option>Vehicle</option>
				</select>
				<br/>
				
				<label><b>Photo</b></label>
				<input type="text" class="form-control" name="photo"/>
								
				<button type="submit" class="btn btn-primary mt-2">Add Product</button>
			</form>
			<hr/>
			<form action="searchProduct" method="GET"> 
				<input type="text" class="form-control" name="stext" style="width:200px; display: inline">
				<button type="submit" class="btn btn-success">Search Product</button>
			</form>
			<hr/>
		</div>
		
		<img src="api/chart">
		
		<hr/>
		<span style="color:red;font-size:18px;font-weight:bold;">${message}</span>	
		<table class="table table-bordered">
		    <thead>
		      <tr>
		        <th>Pid</th>
		        <th>
		        	<a href="sorting?attribute=name&orderBy=${orderBy}">
		        		Name
		        	</a>
		        </th>
		        <th>Price</th>
		       	<th>
		       		<a href="sorting?attribute=category&orderBy=${orderBy}">
		        		Category
		        	</a>
		       	</th>
		       	<th>Photo</th>
		       	<th>Action</th>
		      </tr>
		    </thead>
		    <tbody>
		    <%
		    //This is standard code to access the map
		    List<ProductDTO> productList=(List<ProductDTO>)request.getAttribute("productList");
		    if(productList==null) {
		    	productList=new ArrayList<>();
		    }
		    int count = 1;
		    for(ProductDTO item : productList) {
		    %>
		      <tr>
		        <td><%=item.getPid()%></td>
		        <td><%=item.getName()%></td>
		        <td><%=item.getPrice()%></td>
		        <td><%=item.getCategory()%></td>
		        <td>
		        	<img alt="" src="<%=item.getPhoto()%>" style="height: 100px;">
		        </td>	
		        <td>
		        	<a href="deleteProduct?pid=<%=item.getPid()%>">
		        		<img style="height: 50px" alt="" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDgCtB72sd2csn3h4Xoktuuub7vFQQ-dGBOw&s">
		        	</a>
		        </td>	        
		      </tr>
		      <% } %>
		    </tbody>
		  </table>
	</div>
</body>
</html>