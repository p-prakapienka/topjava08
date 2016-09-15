<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/format" %>

<html>
<head>
    <title>Meal list</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Exceed</th>
        </tr>
        <c:forEach var="item" items="${meals}">
            <tr style="color: ${item.exceed ? "red" : "green"};">
                <td>
                    ${fn:format(item.dateTime)}
                </td>
                <td>${item.description}</td>
                <td>${item.calories}</td>
                <td>${item.exceed}</td>
                <td><a href="meals?do=get&id=${item.id}">Edit</a></td>
                <td><a href="meals?do=delete&id=${item.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

    <form method="post" action="${meal.id eq null ? "meals?do=save" : "meals?do=update"}">
        <input type="hidden" value="${meal.id}" name="id">
        <label>DateTime:
            <input type="datetime-local" name="date" value="${meal.dateTime}">
        </label>
        <label>Description:
            <input type="text" name="description" value="${meal.description}">
        </label>
        <label>Description:
            <input type="number" name="calories" value="${meal.calories}">
        </label>
        <input type="submit">
    </form>
</body>
</html>
