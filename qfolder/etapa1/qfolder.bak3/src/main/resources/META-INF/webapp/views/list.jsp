<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<html>
	<head>
		<title>List</title>
		
		<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="/js/list.js"></script>
	</head>

	<body>
		
		<c:forEach var="item" items="${hosts}">
			<li>${item}</li>
		</c:forEach>		
	</body>
</html>