<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>商品詳細</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>商品の詳細表示</h3>
		<br />
		<table>
			<tr>
				<th>商品名</th>
				<td><c:out value="${itemDTO.name}" /></td>
			</tr>
			<tr>
				<th>商品の色</th>
				<td><c:out value="${itemDTO.color}" /></td>
			</tr>
			<tr>
				<th>メーカー名</th>
				<td><c:out value="${itemDTO.manufacturer}" /></td>
			</tr>
			<tr>
				<th>価格</th>
				<td><c:out value="${itemDTO.price}" />円</td>
			</tr>
			<tr>
				<th>在庫数</th>
				<td><c:out value="${itemDTO.stock}" />個</td>
			</tr>
			</table>
		<c:choose>
		<c:when test = "${itemDTO.stock == 0}">
			<p>売り切れました</p>
		</c:when>
		<c:when test = "${(itemDTO.stock - itemInCartDTO.amount) < 1}">
			<p>これ以上カートに追加できません</p>
		</c:when>
		<c:otherwise>
		<form action='Cart' method='POST'>
			数量
			<select name='amount'>
			<c:choose>
				<c:when test = "${itemInCartDTO == null}">
		    		<c:forEach var="stock" begin="1" end="${itemDTO.stock}" step="1">
        				<option value='<c:out value="${stock}" />'><c:out value="${stock}" /></option>
    				</c:forEach>
				</c:when>
				<c:otherwise>
				    <c:forEach var="stock" begin="1" end="${itemDTO.stock - itemInCartDTO.amount}" step="1">
			        	<option value='<c:out value="${stock}" />'><c:out value="${stock}" /></option>
    				</c:forEach>
				</c:otherwise>
			</c:choose>
			</select>
			<input type='hidden' name='itemId' value='${itemDTO.itemId}' />
			<br /><input type='submit' value='ショッピングカートに入れる' /><br />
		</form>
		</c:otherwise>
		</c:choose>


<%-- //※ログインしていない状態では、ボタンのクリック後、ログイン画面 --%>

		<a href="<%=request.getContextPath() %>/MainServlet">商品検索</a>へ<br />
	</body>
</html>