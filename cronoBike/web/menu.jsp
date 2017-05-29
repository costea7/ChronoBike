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
            file = "arbitro.jspf";
            break;
            
        case 3:
             file = "corredor.jspf";
             break;
        
        case 4:
            file = "admin.jspf";
            break;
        default:
            file = "error.jspf";
    }
    System.out.println(status);
    System.out.println(file);
    //<%@include file="/WEB-INF/jspf/"
    
    //NOTA: hacer direcciones chusta/normal
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
        <h1>Hello World!</h1>
        
    
</html>
