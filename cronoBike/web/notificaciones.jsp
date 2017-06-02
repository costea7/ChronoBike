<%-- 
    Document   : notificaciones
    Created on : 30-may-2017, 15:56:56
    Author     : Diego
--%>

<%@page import="java.util.List"%>
<%@page import="bd.Notificaciones"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="notifi" class="bean.BeanNotificacion" scope="application"/>
<!DOCTYPE html>

<%        
    String user = (String) session.getAttribute("UCI");
    String fReturn = (String) session.getAttribute("return");

    List<Notificaciones> lista = notifi.getNotifications(user, "a");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello <%=user.toString() %></h1>
        <table>
            <tr>
                <th>Categoria</th>
                <th>Fecha</th>
                <th>Descripcion</th>
            </tr>
            <%
            for(int i=0; i<lista.size();i++){%>
                <tr>
                    <td><%= (lista.get(i)).getTipo() %></td>
                    <td><%= (lista.get(i)).fechaString() %></td>  
                    <td><%= (lista.get(i)).getTexto() %></td>
                </tr>
            <%}%>  
        </table>   
        <br/>
        <br/>
        <a href="menu.jsp">Volver</a>
        
    </body>
</html>