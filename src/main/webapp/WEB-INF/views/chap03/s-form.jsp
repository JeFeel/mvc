<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
    <style>
        ul{
            list-style: none;
        }
    </style>
</head>
<body>

    <form action="/hw/s-login-check" method="post">
        <h1>로그인하기</h1>

        <ul>
            <li># 아이디: <input type="text" name="id"></li>
            <li># 비밀번호: <input type="text" name="pw"></li>
        </ul>

        <button type="submit">로그인</button>
    </form>

</body>
</html>