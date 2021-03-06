
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <style><%@include file="/css/styles.css"%></style>
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please add user!
    </div>
    <form method="post" action="/2FIX_war/users">
        <label for="first-name">First Name
            <input class="input-field" type="text" id="first-name" name="first-name">
        </label>
        <label for="last-name">Last name
            <input class="input-field" type="text" id="last-name" name="last-name">
        </label>
        <input type="submit" value="Add User">
    </form>
</div>
</body>
</html>