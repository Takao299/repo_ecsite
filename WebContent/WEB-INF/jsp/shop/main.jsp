<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>商品検索</title>
		<link rel='stylesheet' type='text/css' href='css/style.css' />
	</head>
	<body>
		<h3>商品の検索を行います。</h3>
		<br />
		<form action='SearchResultServlet' method='POST'>
			キーワード<br />
			<input type='text' name='keyword' /><br />
			カテゴリ<br />
			<select name='category'>
				<option selected value='0'>すべて</option>
				<option value='1'>帽子</option>
				<option value='2'>鞄</option>
			</select><br />
			<input type='submit' value='検索' /><br />
		</form>
		<a href='Cart'>ショッピングカートを見る</a><br /><br />
		<c:choose>
			<c:when test="${loginUserInfo == null}">
				<%-- ※ログインしていない場合には以下を表示<br /> --%>
				<a href='<%=request.getContextPath()%>/Login'>ログイン</a>
				<br />
				<br />
			</c:when>
			<c:otherwise>
				<%-- ※ログイン済の場合には以下を表示<br /> --%>
				<a href='<%=request.getContextPath()%>/UpdateUserServlet'>会員情報の変更</a><br />
				<br />
				<a href='<%=request.getContextPath() %>/LogoutServlet'>ログアウト</a><br />
				<br />
			</c:otherwise>
		</c:choose>
	</body>
</html>