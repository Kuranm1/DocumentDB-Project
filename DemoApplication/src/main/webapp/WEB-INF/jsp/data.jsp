<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="bar">
    <p>School System</p>
</div>
<div class="courseStatForm">

    <table class="table table-dark table-striped" >
        <tr>
            <th>Students</th>
        </tr>
        <c:forEach items="${data}" var="stat">
            <tr>
                <td><c:out value="${stat}"/></td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>