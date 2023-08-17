<%-- 
    Document   : login
    Created on : Aug 11, 2023, 3:52:02 PM
    Author     : Owner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login page</title>
    </head>
    <body>
        <h1>${message}</h1>
        <h2>Login</h2>
        <form action="Login" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="submit" name="action" action="login" value="Login"/>
        </form>
        
        <h2>Register new account</h2>
        <form action="Login" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="submit" name="action" action="createUser" value="Register"/>
        </form>
    </body>
</html>
