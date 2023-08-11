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
        <h2>Login</h2>
        <form action="Twitter" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="button" name="action" action="login"/>
            
            <input type="submit" value="Login"/>
        </form>
        
        <h2>Register new account</h2>
        <form action="Twitter" method="post">
            <label>Username</label>
            <input type="text" name="username"/><br>
            <label>Password</label>
            <input type="password" name="password"/><br>
            <input type="button" name="action" action="register"/>
            
            <input type="submit" value="Register"/>
        </form>
    </body>
</html>
