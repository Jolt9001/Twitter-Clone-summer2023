
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <header role="banner">
            
        </header>
        <main role="main">
            <section label="tweet-list">
                <c:forEach var="tweet" items="${tweets}">
                    <article class="tweet">
                        <div class="tweetHead">
                            <img src="GetImage/username${username}">
                            <a href="">${username}</a>
                        </div>
                        <div class="tweetBody">
                            <p></p>
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
