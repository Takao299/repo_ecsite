<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>ショッピングカート内の商品を削除完了</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>以下の商品をショッピングカートから削除しました。</h3>
		<br />
		<c:out value="${itemInCartDTO.item.name}"/><br />
		<c:out value="${itemInCartDTO.item.manufacturer}"/><br />
		<c:out value="${itemInCartDTO.item.price}"/>円<br />
		数量 <c:out value="${itemInCartDTO.amount}"/> 個<br />
		<br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>