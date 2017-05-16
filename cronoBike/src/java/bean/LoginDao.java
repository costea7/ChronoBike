/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;  
import java.sql.*;  
public class LoginDao {  
  
public static boolean validate(LoginBean bean){  
boolean status=false;  
try{  
Connection con=ConnectionProvider.getCon();  
              
PreparedStatement ps=con.prepareStatement(  
    "select * from users where user=? and pass=?");  
  
ps.setString(1,bean.getUser());  
ps.setString(2,bean.getPass());  
              
ResultSet rs=ps.executeQuery();  
status=rs.next();  
              
}catch(Exception e){}  
  
return status;  
  
}  
}  