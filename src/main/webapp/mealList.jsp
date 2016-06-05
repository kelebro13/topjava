<%@ page import="ru.javawebinar.topjava.model.UserMealWithExceed" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); %>
<html>
<head>
    <title>Meal List</title>
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal List</h2>
<div class="container">
    <div class="row">
        <c:if test="${mealList != null}">
        <table class="table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${mealList}" var="i">
                <c:if test="${i.isExceed()}"><tr style="color: red;"></c:if>
                <c:if test="${!i.isExceed()}"><tr style="color: green;"></c:if>
                    <td>${i.getDateTimeToString()}</td>
                    <td>${i.getDescription()}</td>
                    <td>${i.getCalories()}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
    </div>
</div>
</body>
</html>
