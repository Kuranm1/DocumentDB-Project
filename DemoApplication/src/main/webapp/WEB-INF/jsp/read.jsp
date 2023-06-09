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

    <form action="/read" method="post">
        <input type="text" name="conditionKey" id="conditionKey" placeholder="CONDITION KEY">
        <br>
        <input type="text" name="conditionValue" id="conditionValue"placeholder="CONDITION VALUE">
        <br>
        <button type="submit">READ</button>
    </form>
</div>
</body>
</html>