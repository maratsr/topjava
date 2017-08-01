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
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meal list</h2>
    <form style="display:inline; padding:2px" action="${pageContext.request.contextPath}/meals" method="post">
    <button type="submit" name="insert">Add meal</button>
    <hr/>
        <table cellpadding="8" cellspacing="0">
            <tr>
                <td align="right">String pattern</td>
                <td>
                    <input type="text" name="mealsearchtmpl" value="${mealsearchtmpl}">
                </td>
            </tr>
            <tr>
                <td align="right">Date from</td>
                <td><input type="date" name="datepartbegin" value="${begindate}"></td>
                <td><input type="time" name="timepartbegin" value="${begintime}"></td>
            </tr>
            <tr>
                <td align="right">Date to</td>
                <td><input type="date" name="datepartend" value="${enddate}"></td>
                <td><input type="time" name="timepartend" value="${endtime}"></td>
                <td><button type="submit" name="dosearch">Search</button></td>
            </tr>
        </table>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><input type="hidden" name="id" value="${meal.id}">
                    <button type="submit" name="update" value="${meal.id}">Update</button>
                </td>
                <td><input type="hidden" name="id" value="${meal.id}">
                    <button type="submit" name="delete" value="${meal.id}">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    </form>
</section>
</body>
</html>