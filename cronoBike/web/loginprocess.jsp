<%-- 
    Document   : loginprocess
    Created on : 15-may-2017, 18:03:01
    Author     : Iulian
--%>

<!-- llama a loginBean para obtener los parametros de 
     user y pass, yllama a loginDao para comprobar si el usuario ha conseguido
     loguearse.
-->

<%@page import="bean.LoginDao"%>  
<jsp:useBean id="obj" class="bean.LoginBean"/>  
  
<jsp:setProperty property="*" name="obj"/>  
  
<%  
boolean status=LoginDao.validate(obj);  
if(status){  
    out.println("Estás logueado!");  
    session.setAttribute("session","TRUE");  
}  
else{  
    out.print("Error con el login!");  
%>  
<jsp:include page="index.jsp"></jsp:include>  
<%  
}  
%>  