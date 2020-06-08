<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.Meal"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" action='meals'>
    <c:if test="${meal.id != 0}">
        <input type="hidden" readonly="readonly" name="id" value="${meal.id}">
    </c:if>
    <label for="dateTime">Date and Time</label>
    <input id="dateTime" type="datetime-local" name="dateTime" value="${meal.dateTime}">
    <br>
    <label for="description">Description</label>
    <input id="description" type="text" name="description" value="${meal.description}">
    <br>
    <label for="calories">Calories</label>
    <input id="calories" type="number" name="calories" value="${meal.calories}">
    <br>
    <button type="submit">Save</button>
</form>
</body>
</html>