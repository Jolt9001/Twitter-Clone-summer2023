
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- <script type="text/javascript">
    function switchFollow() {
        var elem = document.getElementById("pointer").onclick;
        if (elem.value == "followUser") {
            elem.value = "unfollowUser";
        }
        else {
            elem.value = "unfollowUser";
            elem.action = "followUser";
        }
    }
</script> --%>
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
        <section id="users" class="user-list">
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
                                <input id="pointer" type="hidden" name="action" value="followUser">
                                <input type="hidden" name="followeduid" value="${user.id}"/>
                                <input id="follow-${user.id}" type="submit" value="Follow User" onClick="switchFollow(this)"/>
                                
                                <input id="pointer" type="hidden" name="action" value="unfollowUser">
                                <input type="hidden" name="followeduid" value="${user.id}"/>
                                <input id="follow-${user.id}" type="submit" value="Unfollow User" onClick="switchFollow(this)"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
        <section id="feed" class="tweet-list">
            <h2>Your feed</h2>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <c:forEach var="tweet" items="${tweets}">
                <article id="${tweet.id}" class="tweet">
                    <c:set var="user" value="${TweetModel.getUsername(tweet.user_id)}"/>
                    <c:set var="id" value="${tweet.id}"/>
                    <div class="tweetHead">
                        Tweet ID: ${tweet.id}<br>
                        <%-- <img id="pfp-${tweet.id}" src="${profileImageURL}" alt="pfp" style="display: none"> --%>
                        ${tweet.user_id}
                        <%-- Username: ${user} --%>
                    </div>
                    <div class="tweetBody">
                        ${tweet.text}
                        <c:if test="${!empty tweet.attachment}">
                            <img id="attachment-${tweet.id}" src="GetTweet?tAction=getAttachment" style="display: none">
                        </c:if>
                    </div>
                    <div class="tweetFoot">
                        <form action="Twitter" method="post">
                            <input type="hidden" name="action" value="likeTweet"/>
                            <input type="hidden" name="tweet_id" value="${tweet.id}"/> <!-- Include tweet_id in the form -->
                            <input type="hidden" name="likes" value="${tweet.likes}"/>
                            <input type="submit" value="${tweet.likes} Likes"/>
                        </form>
                    </div><br>
                    <script>
                        $(document).ready(function() {
                            // Check if the image source is loadable
                            var pfpImageSrc = "${profileImageURL}";
                            var pfpImg = new Image();
                            pfpImg.src = pfpImageSrc;
                            pfpImg.onload = function() {
                                // Display the profile image by changing the style
                                $("#pfp-${tweet.id}").attr("src", pfpImageSrc);
                                $("#pfp-${tweet.id}").css("display", "block"); // Show the image
                            };

                            var attachmentImageSrc = "GetTweet?tAction=getAttachment";
                            var attachmentImg = new Image();
                            attachmentImg.src = attachmentImageSrc;
                            attachmentImg.onload = function() {
                                // Display the attachment image by changing the style
                                $("#attachment-${tweet.id}").attr("src", attachmentImageSrc);
                                $("#attachment-${tweet.id}").css("display", "block"); // Show the image
                            };
                        });
                    </script>
                </article>
            </c:forEach>
        </section>
        <section id="create" class="tweet-create">
            <form action="${pageContext.request.contextPath}/Twitter" method="post" enctype="multipart/form-data">
                <input type="text" name="text" placeholder="What's happening?"/>
                <input type="submit" value="Create Post"/><br>
                <input type="file" accept="image/*" name="file"/>
                <c:if test="${!param.file.isEmpty()}">
                    <input type="hidden" name="attached" value="true"/>
                </c:if>
            </form>
        </section>
    </body>
</html>