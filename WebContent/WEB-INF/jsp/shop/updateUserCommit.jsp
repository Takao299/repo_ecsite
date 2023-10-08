<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>会員情報の更新完了</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>以下の会員情報で更新しました。</h3>
		<br />
		<table>
			<tr>
				<th>会員ID</th>
				<td><c:out value="${UserDTO.userId}" /></td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td><c:out value="${UserDTO.password}" /></td>
			</tr>
			<tr>
				<th>お名前</th>
				<td><c:out value="${UserDTO.name}" /></td>
			</tr>
			<tr>
				<th>ご住所</th>
				<td><c:out value="${UserDTO.address}" /></td>
			</tr>
		</table><br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>