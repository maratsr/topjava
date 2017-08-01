<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update meal</title>
</head>
<body>
    <h3>Update meal</h3>
    <form action="${pageContext.request.contextPath}/meals" method="post">
    <table>
        <tr><td>Description:</td><td><input type="text" name="description" value="${meal.description}"/></td></tr>
        <tr><td>Date</td><td><input type="date" name="datepart" value="${meal.dateTime.format(formatter).substring(0,10)}"></td></tr>
        <tr><td>Time</td><td><input type="time" name="timepart" value="${meal.dateTime.format(formatter).substring(11)}"></td></tr>
        <tr><td>Calories:</td><td><input type="number" name="calories" value="${meal.calories}"></td></tr>
        <input type="hidden" name="id" value="${meal.id}">
        <input type="hidden" name="operation" value="updateconfirm">
        <tr><td colspan="2"><button type="submit" name="confirm">Confirm</button></td></tr>
    </table>
</form>
</body>
</html>
