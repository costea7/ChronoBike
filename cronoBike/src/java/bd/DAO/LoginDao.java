/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.DAO;

import bd.conect.ConnectionProvider;
import categorias.Rol;
import java.sql.*;

public class LoginDao {
    private static String SQL_GET_ROL = "select u.Rol from Usuarios u where Id='%s' and Pass='%s'";
    private static String SQL_GET_UCI = "select u.`UCI-ID` from Usuarios u where Id='%s' and Pass='%s'";

    
    
    /**
     * 
     * @param _user
     * @param _pass
     * @return devuelve status siendo:
     *      0 - fallo en la conexión 
     *      1 - usuario no existe
     *      2 - usuario arbitro
     *      3 - usuario corredor
     *      4 - usuario admin
     *      5 - rol desconocido
     */
    public static int validar(String _user, String _pass){
        int respuesta = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_ROL, _user, _pass);
            ps = con.prepareStatement(query);


            rs = ps.executeQuery();
            if (rs.next()) {
                String rol = rs.getString("Rol");
                Rol rolEnum = Rol.valueOf(rol);
                switch (rolEnum) {
                    case ADMINISTRADOR:
                        respuesta = 4;
                        break;
                    case CORREDOR:
                        respuesta = 3;
                        break;
                    case ARBITRO:
                        respuesta = 2;
                        break;
                    default:
                        respuesta = 5;
                }

            } else {
                respuesta = 1;
            }
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
    
    public static String getUCI(String _user, String _pass){
        String respuesta = "";
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_UCI, _user, _pass);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                respuesta = rs.getString("UCI-ID");
            }
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
}
