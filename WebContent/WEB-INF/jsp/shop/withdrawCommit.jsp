<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>退会完了</title>
		<link rel='stylesheet' type='text/css' href='css/shop_style.css' />
	</head>
	<body>
		<h3><c:out value="${name}"/>さん、退会処理を完了しました。</h3>
		<h3>またのご利用をお待ちしております。</h3>
		<a href='<%=request.getContextPath()%>/Login'>ログイン画面</a>へ<br />
	</body>
</html>