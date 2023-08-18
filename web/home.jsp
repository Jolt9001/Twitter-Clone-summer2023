
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h2>TwitterClone</h2>
        <p>Debug: home.jsp accessed.</p>
        <section class="tweet-list">
            <c:forEach var="tweet" items="${tweets}">
                <article class="tweet">
                    <div class="tweetHead">
                        <c:if test="${!filename.isEmpty()}">
                            <img src="GetImage?username${username}">
                        </c:if>
                        <a href="/Profile/${tweet.username}">
                            <c:out value="${tweet.username}"/>
                        </a>
                    </div>
                    <div class="tweetBody">
                        <p><c:out value="${tweet.text}"/></p>
                        <c:if test="${!tweet.attachment.isEmpty()}">
                            <img src="${tweet.attachment}">
                        </c:if>
                    </div>
                    <div class="tweetFoot">
                        <!-- Other tweet footer content here -->
                    </div>
                </article>
            </c:forEach>
        </section>
    </body>
</html>