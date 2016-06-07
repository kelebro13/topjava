<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal List</title>
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<div class="container">
    <div class="row">
        <div class="col-xs-5">
            <form action="mealList" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Id" name="id" required
                    <c:if test="${userMeal != null}"> value="${userMeal.getId()}" readOnly="true"</c:if>
                    >
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="description" name="description" required
                           value="${userMeal.getDescription()}"></div>
                <div class="form-group">
                    <input type="datetime-local" class="form-control" placeholder="Date and Time" name="date" required
                           value="${userMeal.getDateTime()}"></div>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="calories" name="calories" required
                           value="${userMeal.getCalories()}"></div>
                <button type="submit" class="btn btn-primary" name="save">Save</button>
            </form>
        </div>
    </div>
    <hr>
    <div class="row">
        <c:if test="${mealList != null}">
            <table class="table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${mealList}" var="i">
                    <c:if test="${i.isExceed()}"><tr style="color: red;"></c:if>
                    <c:if test="${!i.isExceed()}"><tr style="color: green;"></c:if>
                    <td>${i.getDateTimeToString()}</td>
                    <td>${i.getDescription()}</td>
                    <td>${i.getCalories()}</td>
                    <td>
                        <a href="mealList?action=edit&id=<c:out value="${i.getId()}"/>" class="btn btn-primary">Edit</a>
                    </td>
                    <td>
                        <a href="mealList?action=delete&id=<c:out value="${i.getId()}"/>"
                           class="btn btn-danger">Delete</a>
                    </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>
