
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript">
    function switchFollow(button) {
        if (button.value == "Follow User") {
            button.value = "Unfollow User";
            button.form.action = "unfollowUser";
        } else {
            button.value = "Follow User";
            button.form.action = "followUser";
        }
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h2>TwitterClone Home</h2>
        <c:if test="${!user.filename.isEmpty()}">
            <img src="GetImage?username=${username}" width="200" height="200">
        </c:if>
        <p>${username} logged in.</p>
        <section class="user-list">
            <h2>Followable Users</h2>
            <form action="Twitter" method="post"> <!-- Use a form with action "Twitter" and method "post" -->
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>PFP filename</th>
                        <th>Follow/Unfollow</th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        debug: foreach userlist
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.username}"/></td>
                            <td><c:out value="${user.filename}"/></td>
                            <td>
                                <form action="Twitter" method="post">
                                    <input type="hidden" name="action" value="followUser">
                                    <input id="follow" type="submit" value="Follow User" onClick="switchFollow(this)"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </section>
        <section class="tweet-list">
            Debug: section class tweet-list.
            <c:forEach var="tweet" items="${tweets}">
                Debug: forEach jstl.
                <article class="tweet">
                    <div class="tweetHead">
                        <img src="GetTweet/id${tweet.id}">
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
        </section>
    </body>
</html>