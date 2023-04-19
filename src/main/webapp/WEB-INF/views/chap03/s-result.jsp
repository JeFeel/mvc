<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
     <meta charset="UTF-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
     <h1>${check}</h1>

     <a href="/hw/s-login-form">다시 로그인하기</a>

     <%--     <h1 class="result-title"></h1>--%>


     <%--     <a href="/hw/s-login-form">다시 로그인하기</a>--%>
     <%-- script에서는 검증이 아닌 단순히 switch로 경우를 나누어서 웹에 보여줄 메세지를 선택--%>
     <%--     <script>--%>
     <%--          let result = '${result}';--%>
     <%--          console.log(result);--%>

     <%--          const $h1 = document.querySelector('.result-title');--%>
     <%--          switch (result) {--%>
     <%--               case 'f-id':--%>
     <%--                    $h1.textContent = '아이디가 존재하지 않습니다.';--%>
     <%--                    break;--%>
     <%--               case 'f-pw':--%>
     <%--                    $h1.textContent = '비밀번호가 틀렸습니다.';--%>
     <%--                    break;--%>
     <%--               case 'success':--%>
     <%--                    $h1.textContent = '로그인 성공.';--%>
     <%--                    break;--%>
     <%--          }--%>

     <%--     </script>--%>
</body>
</html>



