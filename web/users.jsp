
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Users</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Password Hash</th>
            </tr>
            <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="$user.id" /></td>
                <td><c:out value="$user.username" /></td>
                <td><c:out value="$user.password" /></td>
            </tr>
            </c:forEach>
        </table>
        
        <h2>Create user</h2>
        <form action="Twitter" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="button" name="action" action="createUser"/>
            
            <input type="submit" value="Create User"/>
        </form>
        
        <h2>Update user</h2>
        <form action="Twitter" method="post">
            <label>UID</label>
            <input type="text" name="id"/><br>
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="button" name="action" action="updateUser"/>
            
            <input type="submit" value="Update User"/>
        </form>
        
        <h2>Delete user</h2>
        <form action="Twitter" method="post">
            <label>UID</label>
            <input type="text" name="id"/><br>
            <input type="button" name="action" action="deleteUser"/>
            
            <input type="submit" value="Delete User"/>
        </form>
    </body>
</html>
