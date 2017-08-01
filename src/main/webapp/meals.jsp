<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Meals list</title>
    <p class="panel panel-default">
        <div><h3>List of meals</h3></div>
        <table border="1">
            <thead><tr><strong>
                <th>Description</th>
                <th>Date and time</th>
                <th>Calories</th>
                <th>Action</th>
            </strong></tr></thead>
            <c:forEach var="meal" items="${mealWithExceeds}">
                <tr style="color:${meal.exceed ? '#FF0000' : '#00CC00'}">
                    <td>${meal.description}</td>
                    <td>${meal.dateTime.format(formatter)}</td>
                    <td align="right">${meal.calories}</td>
                    <td>
                        <form style="display:inline; padding:2px" action="${pageContext.request.contextPath}/meals" method="post">
                            <input type="hidden" name="operation" value="delete">
                            <input type="hidden" name="id" value="${meal.id}">
                            <button type="submit" name="delete${meal.id}">delete</button>
                        </form>
                        <form style="display:inline; padding:2px" action="${pageContext.request.contextPath}/meals" method="post">
                            <input type="hidden" name="operation" value="updateform">
                            <input type="hidden" name="id" value="${meal.id}">
                            <button type="submit" name="update${meal.id}">update</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div><form action="${pageContext.request.contextPath}/meals" method="post">
            <h4>Enter values for meal creating:</h4>
            <table>
                <tr><td>Description:</td><td><input type="text" name="description"/></td></tr>
                <tr><td>Date</td><td><input type="date" name="datepart"></td></tr>
                <tr><td>Time</td><td><input type="time" name="timepart"></td></tr>
                <tr><td>Calories:</td><td><input type="number" name="calories"></td></tr>
                <tr><td colspan="2"><button type="submit" name="addmeal">Add meal</button></td></tr>
            </table>
            <input type="hidden" name="operation" value="create">
        </form></div>
    </div>
</head>
<body>

</body>
</html>
