
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <p>redirect test</p>
        <main action="Twitter" method="post">
            <p>redirect test</p>
            <section label="tweet-list">
                <c:forEach var="tweetId" items="${tweet}">
                    <article class="tweet">
                        <div class="tweetHead">
                            <c:when test="">
                                <c:if test="${!filename.isEmpty()}">
                                    <img src="GetImage/username${username}">
                                </c:if>
                                <a href="/Profile/${username}">
                                    <c:out value=""/>
                                </a>
                            </c:when>
                            <p><c.out value="$tweet.text"/></p>
                        </div>
                        <div class="tweetBody">
                            <p>${text}</p>
                            <c:if test="${!attachment.isEmpty()}">
                                <img src="">
                            </c:if>
                        </div>
                        <div class="tweetFoot">
                            
                        </div>
                    </article>
                </c:forEach>
            </section>
        </main>
    </body>
</html>
