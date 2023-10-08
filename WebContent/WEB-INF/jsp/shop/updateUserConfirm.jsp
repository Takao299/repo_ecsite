<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>会員情報の更新</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>以下の会員情報で更新しますか？</h3>
		<br />
		<form action='<%=request.getContextPath()%>/UpdateUserCommitServlet' method='POST'>
			<table>
				<tr>
					<th>会員ID</th>
					<td> <c:out value="${UserDTO.userId}"/> </td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td> <c:out value="${UserDTO.password}"/> </td>
				</tr>
				<tr>
					<th>お名前</th>
					<td> <c:out value="${UserDTO.name}"/> </td>
				</tr>
				<tr>
					<th>ご住所</th>
					<td> <c:out value="${UserDTO.address}"/> </td>
				</tr>
				<tr>
					<td colspan='2'>
					<input type='hidden' name='password' value='${UserDTO.password}' />
					<input type='hidden' name='name' value='${UserDTO.name}' />
					<input type='hidden' name='address' value='${UserDTO.address}' />
					<input type='submit' value='変更' />
					</td>
				</tr>
			</table>
		</form>
		<br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>