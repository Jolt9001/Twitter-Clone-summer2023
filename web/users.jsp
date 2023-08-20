
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script type="text/javascript">
    function switchFollow() {
        var elem = document.getElementById("follow").onclick;
        if (elem.value == "Follow User") {
            elem.value = "Unfollow User";
            elem.action = "unfollowUser";
        }
        else {
            elem.value = "Follow User";
            elem.action = "unfollowUser";
        }
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User page</title>
    </head>
    <body>
        <h2>Users</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Password Hash</th>
                <th>Profile Picture Filename</th>
                <th>Follow/Unfollow</th>
            </tr>
            <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.password}"/></td>
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

        <h2>Create user</h2>
        <form action="UserManager" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="submit" name="action" action="createUser" value="Create User"/>
        </form>
        
        <h2>Update user</h2>
        <form action="UserManager" method="post">
            <label>UID</label>
            <input type="text" name="id"/><br>
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="submit" name="action" action="updateUser" value="Update User"/>
        </form>
        
        <h2>Delete user</h2>
        <form action="UserManager" method="post">
            <label>UID</label>
            <input type="text" name="id"/><br>
            <input type="submit" name="action" action="deleteUser" value="Delete User"//>
        </form>
    </body>
</html>
