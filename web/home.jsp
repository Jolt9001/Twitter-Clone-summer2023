
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
            <img src="GetImage?username=${username}" width="200" height="200"><br>
        </c:if>
        ${username} logged in.
        <section class="user-list">
            <h2>Followable Users</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>PFP filename</th>
                    <th>Follow/Unfollow</th>
                </tr>
                <c:forEach var="user" items="${users}">
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
        </section>
        <section class="tweet-list">
            <h2>Your feed</h2>
            <c:forEach var="tweet" items="${tweets}">
                <article class="tweet">
                    <c:set var="user" value="${TweetModel.getUsername(tweet.user_id)}"/>
                    <div class="tweetHead">
                        <p>DEBUG STATEMENTS</p>
                        Tweet ID: ${tweet.id}<br>
                        <img src="${profileImageURL}" alt="pfp">
                        ${tweet.user_id}
                        Username: ${user}
                    </div>
                    <div class="tweetBody">
                        ${tweet.text}
                        <c:if test="${!empty tweet.attachment}">
                            <img src="GetTweet?tAction=">
                        </c:if>
                    </div>
                    <div class="tweetFoot">
                        <input type="button" name="like" value ="Like"/>
                    </div>
                </article>
            </c:forEach>
        </section>
        <br><br>
        <section class="tweet-create">
            <form action="${pageContext.request.contextPath}/Upload" method="post" enctype="multipart/form-data">
                <input type="text" name="text" placeholder="What's happening?"/>
                <input type="submit" value="Create Post"/><br>
                <input type="file" accept="image/*" name="file"/>
            </form>
        </section>
    </body>
</html>