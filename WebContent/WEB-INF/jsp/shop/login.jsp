<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>会員ログイン</title>

<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
</head>
<body>
	<h3>ログインしてください。</h3>
	<c:if test="${errorMessage == 1}">
	ユーザ認証に失敗しました。
</c:if>
	<br />
	<form action='Login' method='POST'>
		<table>
			<tr>
				<th>会員ID</th>
				<td><input type='text' class='id' name='id' /></td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td><input type='password' class='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input type='submit' value='ログイン' /></td>
			</tr>
		</table>
	</form>
	<a href='<%=request.getContextPath()%>/RegisterUserServlet'>会員登録</a>
	<br />
</body>
</html>