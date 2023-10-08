<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>ショッピングカート内の商品を削除確認</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>以下の商品をショッピングカートから削除してよろしいですか？</h3>
		<br />
		<c:out value="${itemInCartDTO.item.name}"/><br />
		<c:out value="${itemInCartDTO.item.manufacturer}"/><br />
		<c:out value="${itemInCartDTO.item.price}"/>円<br />
		数量 <c:out value="${itemInCartDTO.amount}"/> 個<br />
		<form action='<%=request.getContextPath()%>/RemoveFromCartCommitServlet' method='POST'>
			<input type='hidden' name='itemId' value='${itemInCartDTO.item.itemId}' />
			<input type='submit' value='削除する' /><br />
		</form>
		<br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>