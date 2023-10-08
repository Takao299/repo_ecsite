<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>検索結果</title>
<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
</head>
<body>
	<h3>
		キーワード "
		<c:out value="${itemName}" />
		" カテゴリ "
		<c:out value="${cateId.name}" />
		" の検索結果
	</h3>
	<br />

	<table border="1">
		<tr>
			<th>商品名</th>
			<th>商品の色</th>
			<th>メーカー名</th>
			<th>価格</th>
		</tr>

		<%-- request.setAttribute("itemlist",list)で取り出したコレクションに対し、
	１回ループが回るごとに変数『item』に取り出した要素を格納し、利用する。 --%>
		<c:forEach var="item" items="${itemlist}">
			<tr>
				<%-- 『item.itemId』は『item.getItemId()』に変換される --%>
				<td><a href="<%=request.getContextPath() %>/ItemDetailServlet?itemId=<c:out value='${item.itemId}'/>">
				 <c:out value="${item.name}" /></a> <sup><c:if test ="${item.recommended}">オススメ</c:if></sup></td>
				<td><c:out value="${item.color}" /></td>
				<td><c:out value="${item.manufacturer}" /></td>
				<td><c:out value="${item.price}" />円</td>
				<%-- 商品詳細リンクを生成するやりかた。リンクはGETでおくるので『？』の後に
			パラメータ情報をつくる。--%>

			</tr>
		</c:forEach>
	</table>


	<br /> ページ
	<c:forEach var ="i" begin = "1" end = "${pageNumber}" step = "1">
	<a href='<%=request.getContextPath() %>/SearchResultServlet?page=<c:out value="${i}" />&keyword=${itemName}&category=${cateId.categoryId}'><c:out value="${i}" /></a>
	</c:forEach>

<%--今回は使いません
	<a href='<%=request.getContextPath() %>/SearchResultServlet?page=2&keyword=${itemName}&category=${cateId.categoryId}'>2</a>
	<a href='<%=request.getContextPath() %>/SearchResultServlet?page=3&keyword=${itemName}&category=${cateId.categoryId}'>3</a>
	<a href='<%=request.getContextPath() %>/SearchResultServlet?page=4&keyword=${itemName}&category=${cateId.categoryId}'>4</a>
	<a href='searchResult.html?keyword=something&category=all&page=2'>次へ</a>--%>

	<br />
	<br />
	<a href="<%=request.getContextPath() %>/MainServlet">商品検索</a>へ
	<br />
</body>
</html>