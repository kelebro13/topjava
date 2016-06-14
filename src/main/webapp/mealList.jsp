<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
</head>
<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="index.html">Подсчет калорий</a>
        </div>
        <div class="navbar-collapse collapse">
            <a class="btn btn-primary navbar-btn navbar-right" href="index.html">Выйти</a>
        </div>
    </div>
</div>
<div style="margin-top: 50px;"></div>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <form action="meals" method="post">

                    <div class="form-group">
                        <div class="container">
                        <div class="col-sm-2"><b>From Date:</b></div>
                        <div class="col-sm-3"><input class="form-control" type="date" name="startDate"></div>
                        <div class="col-sm-2"><b>To Date:</b></div>
                        <div class="col-sm-3"><input class="form-control" type="date" name="endDate"></div>
                    </div>
                </div>
                    <div class="form-group">
                        <div class="container">
                        <div class="col-sm-2"><b>From Time:</b></div>
                        <div class="col-sm-3"><input class="form-control" type="time" name="startTime"></div>
                        <div class="col-sm-2"><b>To Time:</b></div>
                        <div class="col-sm-3"><input class="form-control" type="time" name="endTime"></div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="container">
                        <div class="col-sm-10">
                        <input type="submit" class="btn btn-primary pull-right" value="Поиск">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-xl-12">
                <a class="btn btn-info" href="meals?action=create">Add Meal</a>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Calories</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${mealList}" var="meal">
                        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.UserMealWithExceed"/>
                        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                            <td>
                                    <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                    <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                    ${fn:formatDateTime(meal.dateTime)}
                            </td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td><a class="btn btn-primary btn-sm" href="meals?action=update&id=${meal.id}">Edit</a></td>
                            <td><a class="btn btn-danger btn-sm" href="meals?action=delete&id=${meal.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>


</body>
</html>