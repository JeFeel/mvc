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

        .wrap{
            display: flex;
            justify-content: center;

        }
        .wrap .main {
            width: fit-content;
            padding: 20px;
            /*align-content: center;*/
            justify-content: center;
            border-color: orange;
            box-shadow: 3px 3px 3px 3px orange;
            text-align: center;


        }

        ul{
            list-style: none;
        }
    </style>
</head>
<body>
    <div class="wrap">
        <section class="main">
            <h1>${s.name}님 성적 정보</h1>
            <ul>
                <li># 국어: ${s.kor}점</li>
                <li># 영어: ${s.eng}점</li>
                <li># 수학: ${s.math}점</li>
                <li># 총점: ${s.total}점</li>
                <li># 평균: ${s.average}점</li>
                <li># 학점: ${s.grade}점</li>
            </ul>
            <button id="go-to-list" type="button">목록</button>
            <button id="modify" type="button">수정</button>
        </section>
    </div>
    <script>
        const $list = document.getElementById('go-to-list');
        $list.addEventListener("click", e => {
            window.location.href = '/score/list';
        })

        const $modify = document.getElementById('modify');
        $modify.addEventListener("click", e => {
            window.location.href = '/score/modify?stuNum=${s.stuNum}';
        })
    </script>
</body>
</html>