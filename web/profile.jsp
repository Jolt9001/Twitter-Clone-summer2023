
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <h2>Hello, ${username}!</h2>
        <c:if test="${!filename.isEmpty()}">
            <img src="GetImage/username${username}" width="150" height="150"/>
        </c:if>
        <h3>Upload a profile picture!</h3>
        <form action="/Upload" method="post" enctype="multipart/form-data">
            <div id="data">
                <input type="file" accept="image/*" name="file"/>
            </div>
            <div id="buttons">
                <label>&nbsp;</label>
                <input type="submit" value="Upload"><br>
            </div>
        </form>
    </body>
</html>
