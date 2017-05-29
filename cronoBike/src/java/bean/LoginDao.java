/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import categorias.Rol;
import java.sql.*;

public class LoginDao {

    /**
     *
     * @param bean
     * @return devuelve status siendo: 0 - fallo en la conexión 1 - usuario no
     * existe 2 - usuario arbitro 3 - usuario corredor 4 - usuario admin 5 - rol
     * desconocido
     */
    public static int validate(LoginBean bean) {
        int respuesta = 0;
        try {
            Connection con = ConnectionProvider.getCon();

            PreparedStatement ps = con.prepareStatement(
                    "select * from Usuarios where Id=? and Pass=?");

            ps.setString(1, bean.getUser());
            ps.setString(2, bean.getPass());

            ResultSet rs = ps.executeQuery();
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
            return respuesta;
        }

    }
}
