
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
    <h2>Hello, ${username}!</h2>
    <c:if test="${!user.filename.isEmpty()}">
        <img src="GetImage?username=${username}" width="150" height="150">
    </c:if>
    <h3>Upload a profile picture!</h3>
    <form action="${pageContext.request.contextPath}/UploadPFP" method="post" enctype="multipart/form-data">
        <div id="data">
            <input type="file" accept="image/*" name="file"/>
        </div>
        <div id="buttons">
            <label>&nbsp;</label>
            <input type="submit" value="Upload"><br>
        </div>
    </form>
    <h3>Your posts</h3>
    <c:forEach var="tweet" items="${tweets}">
        <article class="tweet">
            <div class="tweetHead">
                <img src="GetTweet/id=${tweet.id}">
                <p>${tweet.user.GetUsername()}</p>
            </div>
            <div class="tweetBody">
                <p>${text}</p>
                <c:if test="${!empty tweet.attachment}">
                    <img src="${tweet.getAttachment()}">
                </c:if>
            </div>
            <div class="tweetFoot">
                
            </div>
        </article>
    </c:forEach>
    </body>
</html>
