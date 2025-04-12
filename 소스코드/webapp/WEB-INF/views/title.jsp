<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <!-- Bootstrap icons-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="/resources/css/styles.css?after" rel="stylesheet" />
    <link href="/resources/css/styles2.css" rel="stylesheet" />

    <title>영화 추천 사이트</title>
</head>
<body>

<header class="header">
    <div>
        <h1 class="mt-5">영화 추천 사이트</h1>
    </div>
</header>

<!-- Section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 mt-5">

        <!-- form -->
        <h3>유저의 ID</h3>
        <form class="form-login" action="/" method="get">
            <input type="text" class="input" name="userId" placeholder="User Id" required>
            <button class="btn" type="submit">입력</button>
        </form>
        <br><br><br>

        <!-- user info -->
        <h4>사용자 정보</h4>
        <table style="border: 1px solid">
            <th>userId</th>
            <th>age</th>
            <th>gender</th>
            <th>occupation</th>
            <th>ZIPCODE</th>
            <tr style="border: 1px solid">
                <td>${user.userId}</td>
                <td>${user.age}</td>
                <td>${user.gender}</td>
                <td>${user.occupation}</td>
                <td>${user.ZIPCODE}</td>
            </tr>
        </table>
        <br><br><br>

        <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
            <c:forEach var="movieItem" items="${movies}">
                <div class="col mb-5">
                    <div class="card h-100">
                        <!-- Product image-->
                        <!-- Product details-->
                        <div class="card-body p-4">
                            <div class="text-center">
                                <!-- Product name-->
                                <h5 class="fw-bolder">${movieItem.getMovieTitle()}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>


<div class="container">
    <h4>같은 직업을 가진 사람들이 좋아하는 영화 Top 10</h4>
    <ul>
        <c:forEach var="occupationMovieItem" items="${occupationMovies}">
            <li>${occupationMovieItem.getMovieTitle()}</li>
        </c:forEach>
    </ul>
    <br>
    <h4>같은 연령대 사람들이 좋아하는 영화 Top 10</h4>
    <ul>
        <c:forEach var="ageGroupMovieItem" items="${ageGroupMovies}">
            <li>${ageGroupMovieItem.getMovieTitle()}</li>
        </c:forEach>
    </ul>
    <br>
    <h4>같은 성별을 가진 사람들이 좋아하는 영화 Top 10</h4>
    <ul>
        <c:forEach var="genderMovieItem" items="${genderMovies}">
            <li>${genderMovieItem.movieTitle}</li>
        </c:forEach>
    </ul>
    <br>
    <h4>피어슨 상관계수를 이용한 추천 알고리즘에 의한 영화 Top 10</h4>
    <ul>
        <c:forEach var="recommendedMovieItem" items="${recommendedMovies}">
           <li>${recommendedMovieItem.getMovieTitle()}</li>
        </c:forEach>
    </ul>
    <br>
    <h4>코사인 유사도를 이용한 추천 알고리즘에 의한 영화 Top 10</h4>
    <ul>
        <c:forEach var="RecommendedMovie_Cosine_Item" items="${recommendedMovies_Cosine}">
            <li>${RecommendedMovie_Cosine_Item.getMovieTitle()}</li>
        </c:forEach>
    </ul>
    <br>


</div>


<!-- Footer-->
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="/resources/js/scripts.js"></script>
</body>
</html>
