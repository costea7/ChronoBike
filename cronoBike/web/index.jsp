<%-- 
    Document   : index
    Created on : 02-may-2017, 16:47:09
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crono bike</title>
    
    <script type="text/javascript">
        
        
    var chatIn, chatOut, webSocket;
    document.addEventListener("DOMContentLoaded", 
    function() {
	chatIn = document.getElementById("message-input");
	chatOut = document.getElementById("message-output");
	
	webSocket = new WebSocket("ws://localhost:8080/cronoBike/simple");
	webSocket.onopen = function() {
		console.log("Socket is now open.");
	};
	webSocket.onerror = function() {
		console.log("There was an error.");
	};
	webSocket.onmessage = function(e) {
		console.log("Got message: " + e.data);
		var p = document.createElement("p");
		p.innerHTML = e.data;
		chatOut.appendChild(p);
	}
	chatIn.addEventListener("keydown", function(e) {
		if (e.keyCode == 13) {
			console.log("Entered: " + this.value);
			webSocket.send(this.value);
			this.value = "";
		}
	});
      });
    </script>
    </head>

    <body>
        <h1>WIP index para cronobike</h1>
        <form action="loginServlet" method="post">  
            Usuario:
            <input type="text" name="user" value="">
            <br>
            Contraseña:
            <input type="password" name="pass" value="">
            <br>
            <input type="submit" value="Enviar">
              
            
         </form>
          <br>

        <div id="message-output" style="width: 500px; height: 200px; overflow: scroll; background: white">
        </div>
        <input type="text" id= "message-input" style="width: 500px;"/>
          
    </body>
</html>
