<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>ショッピングカート内の商品を全て削除確認</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>ショッピングカートから全ての商品を削除してよろしいですか？</h3>
		<br />

		<form action='<%=request.getContextPath()%>/RemoveFromCartAllCommitServlet' method='GET'>
			<input type='submit' value='全て削除する' /><br />
		</form>
		<br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>