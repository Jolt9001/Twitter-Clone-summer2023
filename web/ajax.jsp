
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript">
    function loadAjax() {
        var username = document.getElementById("username").value;
        var url = "/twitterstart/GetImage?username=" + username;
        console.log(url);
        if (window.HttpRequest) {
            request = new XMLHttpRequest;
        }
        try {
            request.onreadystatechange = sendInfo;
            request.responseType = "blob";
            request.open("GET", url, true);
            request.send();
        } catch (e) {
            console.log("Unable to connect to server")
        }
    }
    
    function sendInfo() {
        if (request.readyState == 4) {
            var result = document.getElementById("result");
            var img = document.createElement('img');
            img.length = 200;
            img.width = 100;
            img.arc = window.URL.createObjectURL(request.response);
            result.appendChild(img);
        }
    }
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Images with AJAX</title>
    </head>
    <body>
        <h1>Image from database</h1>
        <div id="tata">
            <label>Username</label>
            <input type="text" id="username" name="username"><br>
        </div>
        <div id="buttons">
            <label>&nbsp;</label>
            <input type="button" onclick="loadAjax()" value="Fetch"><br>
        </div>
        <p id="result"></p>
    </body>
</html>
