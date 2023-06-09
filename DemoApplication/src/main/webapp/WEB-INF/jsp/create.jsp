<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="styles.css" />
</head>
<body>
<div class="bar">
    <p>School System</p>
</div>
<div class="courseStatForm">
    <p><font color="red">${errorMessage}</font></p>
    <p><font color="green">${message}</font></p>
    <form action="/create" method="post">
        <input type="text" name="id" id="id" placeholder="STUDENT ID">
        <br>
        <input type="text" name="name" id="password"placeholder="STUDENT NAME">
        <br>
        <input type="text" name="major" id="major"placeholder="STUDENT MAJOR">
        <br>
        <input type="text" name="year" id="year"placeholder="STUDENT YEAR">
        <br>
        <input type="text" name="gpa" id="gpa"placeholder="STUDENT GPA">
        <br>
        <button type="submit">INSERT</button>
    </form>
</div>
</body>
</html>