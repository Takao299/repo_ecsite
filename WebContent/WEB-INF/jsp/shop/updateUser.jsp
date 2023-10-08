 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>会員情報</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3>会員情報</h3>
		<br />
		<c:if test="${errorMessage == 1}">
			<span class="red" >パスワード入力が間違っています。</span>
		</c:if>
		<c:if test="${errorMessage == 2}">
			<span class="red" >入力したIDはすでに登録されています。</span>
		</c:if>
		<form action='<%=request.getContextPath()%>/UpdateUserConfirmServlet' method='POST'>
			<table>
				<tr>
					<th>会員ID</th>
					<td> <c:out value="${UserDTO.userId}"/> </td>
				</tr>
				<tr>
					<th>お名前</th>
					<td><input type='text' name='name' value='${UserDTO.name}' class='text' /></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td><input type='password' name='password1' class='password' /></td>
				</tr>
				<tr>
					<th>パスワード(確認)</th>
					<td><input type='password' name='password2' class='password' /></td>
				</tr>
				<tr>
					<th>ご住所</th>
					<td><input type='text' name='address' value='${UserDTO.address}' class='text' /></td>
				</tr>
				<tr>
					<td colspan='2'><input type='submit' value='変更' /></td>
				</tr>
			</table>
		</form>
		<a href='<%=request.getContextPath()%>/WithdrawConfirmServlet'>退会する</a><br /><br />
		<a href='<%=request.getContextPath()%>/PurchaseHistoryServlet'>購入履歴</a>へ<br />
		<a href='<%=request.getContextPath()%>/MainServlet'>商品検索</a>へ<br />
	</body>
</html>