<%-- 
    Document   : menuU
    Created on : 08-may-2017, 18:11:34
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%
    int status = (Integer)session.getAttribute("Rol");
    String file = "";
    switch (status){
         
        case 2: 
            %>
            <%@include file="/WEB-INF/jspf/arbitro.jspf"%>
            <%
            break;
        case 3:
            %>
            <%@include file="/WEB-INF/jspf/corredor.jspf"%>
            <%
            break;
        case 4:
             %>
             <%@include file="/WEB-INF/jspf/admin.jspf"%>
             <%
             break;    
         //nunca se podría dar un estado que no fuera 2,3 o 4    
         /* default:
               file = "error.jspf";*/
    }

    
    System.out.println(">Return: " + file);
   // String a = (String) session.getAttribute("user");
    //System.out.println(">User: " + session.getId());
    //System.out.println(">User: " + a);
    //NOTA: hacer direcciones chusta/normal
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="notificaciones.jsp" method="post">  
            <input type="submit" value="Ver notificac">            
        </form>
    </body>





</html>
