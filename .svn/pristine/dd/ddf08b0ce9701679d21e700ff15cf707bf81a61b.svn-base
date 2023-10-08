<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>ショッピングカート</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>


	<h3>ショッピングカート内の商品一覧</h3>
	<br>


	<c:set var="loop_flag" value="false" />
	<c:forEach var="val" items="${itemInCartDTO}" varStatus="status">
	    <c:if test="${not loop_flag }">
	        <c:if test = "${val.item.stock < val.amount}">
				<span class="red" >在庫が不足している商品があります</span>
	            <c:set var="loop_flag" value="true" />
	        </c:if>
	    </c:if>
	</c:forEach>


	<table border="1">
		<tbody><tr>
			<th>商品名</th>
			<th>商品の色</th>
			<th>メーカー名</th>
			<th>単価</th>
			<th>数量</th>
			<th>
				<c:if test="${itemInCartDTO.size() != 0}">
					<a href="<%=request.getContextPath()%>/RemoveFromCartAllConfirmServlet">全削除</a>
					<%-- <a href="<%=request.getContextPath()%>/WEB-INF/jsp/shop/removeFromCartAllConfirm.jsp">全削除</a> --%>
					<%-- <a href="<% request.getRequestDispatcher("removeFromCartAllConfirm.jsp").forward(request, response); %>">全削除</a> --%>
				</c:if>
			</th>
		</tr>

		<c:forEach var="val" items="${itemInCartDTO}">
			<tr>
				<td> <c:out value="${val.item.name}"/> </td>
				<td> <c:out value="${val.item.color}"/></td>
				<td> <c:out value="${val.item.manufacturer}"/>
				<td> <c:out value="${val.item.price}"/>円</td>
				<td>
					<c:choose>
						<c:when test = "${val.item.stock < val.amount}">
							<span class="red" > <c:out value="${val.amount}"/> </span>
						</c:when>
						<c:otherwise>
							<c:out value="${val.amount}"/>
						</c:otherwise>
					</c:choose>
				個
				</td>
				<td><a href="<%=request.getContextPath()%>/RemoveFromCartConfirmServlet?itemId=${val.item.itemId}">削除</a></td>

				<c:if test = "${val.item.stock < val.amount}">
				<td>

				<form action='Cart' method='POST'>
					<select name='amount'>
    					<c:forEach var="stock" begin="1" end="${val.item.stock}" step="1">
        					<option value='<c:out value="${stock-val.amount}" />'>	<c:out value="${stock}" />	</option>
    					</c:forEach>
					</select>
					<input type='hidden' name='itemId' value='${val.item.itemId}' />
					<input type='submit' value='数量を変更する' /><br />
				</form>

				</td>
				</c:if>
			</tr>
		</c:forEach>

	</tbody></table>
	合計<c:out value="${sum}" />円
	<br>

	<c:if test="${itemInCartDTO.size() != 0}">
		<form action="<%=request.getContextPath()%>/PurchaseConfirm" method="POST">
			<input type="submit" value="購入する">
		</form>
	</c:if>

	<br>
	<a href="<%=request.getContextPath()%>/MainServlet">商品検索</a>へ
	<br>

	</body>
</html>


<%--

ｑ

正解？のhtmlを開くと以下の通りで記述されているのですが
<td><a href="http://3.114.182.40:8080/ecsite/RemoveFromCartConfirmServlet?itemId=7">削除</a></td>

サーブレットのURLの後に?や&で変数を入れることできるようです
<a href="リンク先URL?変数名1=値1&変数名2=値2&変数名3=値3&...">リンクメッセージ</a>


後はサーブレットでgetパラメーターに変数名を引数に入れると値が取れるかも
int itemId = Integer.parseInt(request.getParameter("itemId"));


参考
https://forum.samuraiz.co.jp/samu06/01.html
https://techacademy.jp/magazine/44814




	<c:forEach var="val" items="${itemInCartDTO}">
		<c:if test = "${val.item.stock < val.amount}">
			在庫が不足している商品があります
			<br>
		</c:if>
	</c:forEach>


 --%>