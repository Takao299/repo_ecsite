<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>購入履歴</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>購入履歴の一覧</h3>
		<br />
		<table>
			<tr>
				<th>注文日</th>
				<th>購入商品</th>
				<th>配送先</th>
				<th></th>
			</tr>
			<c:forEach var="val1" items="${PurchaseList}">
			<tr>
				<td><c:out value="${val1.purchasedDate}"/></td>
				<td>
					<table border="1">
					<%-- <tbody>--%>
						<tr>
							<th>商品名</th>
							<th>色</th>
							<th>メーカー</th>
							<th>単価</th>
							<th>数量</th>
						</tr>
						<c:forEach var="val2" items="${val1.purchasedItems}">
						<tr>
							<td> <c:out value="${val2.item.name}"/> </td>
							<td> <c:out value="${val2.item.color}"/></td>
							<td> <c:out value="${val2.item.manufacturer}"/> </td>
							<td> <c:out value="${val2.item.price}"/>円</td>
							<td> <c:out value="${val2.amount}"/>個</td>
						</tr>
						</c:forEach>
					<%-- </tbody> --%>
					</table>
				</td>
				<td>
					<c:choose>
						<c:when test = "${empty val1.destination}">		<%--null or 空文字の場合true 購入確認画面で別住所「空白」で購入した場合どうなるのかな？ --%>
							<p>自宅</p>
						</c:when>
						<c:otherwise>
							<c:out value="${val1.destination}"/>
						</c:otherwise>
					</c:choose>
				</td>
				<td><a href="<%=request.getContextPath()%>/PurchaseCancelConfirmServlet?purchaseId=${val1.purchaseId}">キャンセル</a></td>
			</tr>
			</c:forEach>
		</table>
		<br /><br />
	<a href="<%=request.getContextPath() %>/MainServlet">商品検索</a>へ
	</body>
</html>