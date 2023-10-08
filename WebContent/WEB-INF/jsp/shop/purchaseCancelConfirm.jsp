<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>注文済み商品のキャンセル</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>以下の注文をキャンセルしてよろしいですか？</h3>
		<br />
		<table>
			<tr>
				<th>注文日</th>
				<td><c:out value="${PurchaseDTO.purchasedDate}"/></td>
			</tr>
			<tr>
				<th>購入商品</th>
				<td>
					<table>
						<tr>
							<th>商品名</th>
							<th>色</th>
							<th>メーカー</th>
							<th>単価</th>
							<th>数量</th>
						</tr>
						<c:forEach var="val" items="${PurchaseDTO.purchasedItems}">
						<tr>
							<td> <c:out value="${val.item.name}"/> </td>
							<td> <c:out value="${val.item.color}"/></td>
							<td> <c:out value="${val.item.manufacturer}"/> </td>
							<td> <c:out value="${val.item.price}"/>円</td>
							<td> <c:out value="${val.amount}"/>個</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<th>配送先</th>
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
			</tr>
			<tr>
				<td colspan='2'>
					<form action='<%=request.getContextPath()%>/PurchaseCancelCommitServlet' method='POST'>
						<input type='hidden' name='purchaseId' value='${PurchaseDTO.purchaseId}' />
						<input type='submit' value='キャンセル' /><br />
					</form>
				</td>
			</tr>
		</table>
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>