<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<html>
	<head>
		<title>Home</title>
		<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
		
		<script type="text/javascript" src="/js/index.js"></script>

		<script type="text/javascript" src="/js/draganddrop.js"></script>
		<link rel="stylesheet" type="text/css" href="/css/draganddrop.css">
	</head>

	<body>
		
		<div id="message"></div>
		<p>
			<spring:message code="hello" />	: ${ip}
		</p>
		<div id="status1"></div>
		<hr>
		<div id="dragandrophandler" style="width:100%; height:70%">
			<ul id="listfile">
				<c:forEach var="item" items="${list}">
					<li><a href="javascript:download('${item}','${ip}');">${item}</a></li>
				</c:forEach>
			</ul>
		</div>
		
	</body>
</html>