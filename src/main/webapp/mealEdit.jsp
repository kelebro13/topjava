<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
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
    <div class="col-sm-6">
    <h3>Edit meal</h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.UserMeal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input class="form-control" type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input class="form-control" type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input class="form-control" type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button class="btn btn-primary" type="submit">Save</button>
        <button class="btn btn-danger" onclick="window.history.back()">Cancel</button>
    </form>
        </div>
    </div>
    </div>
</body>
</html>
