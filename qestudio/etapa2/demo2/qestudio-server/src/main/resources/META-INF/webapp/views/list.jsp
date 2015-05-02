<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Home</title>
</head>

<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.min.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
		$.ajax({
			url:"json",
			success:function(result){
				$.each(result, function(i, obj) {
					$("#ul").append($("<li>").text(obj));
				});
			}
		});
	});

</script>

<body>
<h1>Hello world! This is a JSP.</h1>
<p>Here are some items:</p>
<ul id="ul">
</ul>

</body>
</html>
