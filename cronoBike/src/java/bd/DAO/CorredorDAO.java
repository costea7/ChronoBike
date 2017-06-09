package bd.DAO;

import bd.conect.ConnectionProvider;
import categorias.CategoriaCorredor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Diego
 */
public class CorredorDAO {
    /**
     * Mover esto al consumidor de la cola
     */
    private static String SQL_BAN_UCI = "UPDATE  Corredores SET Sancionado='1' WHERE `UCI-ID`='%s'";
    private static String SQL_BAN_NAME = "UPDATE  Corredores SET Sancionado='1' WHERE Nombre='%s' AND Apellido1='%s' AND Apellido2='%s'";
    private static String SQL_UBAN_UCI = "UPDATE  Corredores SET Sancionado='0' WHERE `UCI-ID`='%s'";
    private static String SQL_UBAN_NAME = "UPDATE  Corredores SET Sancionado='0' WHERE Nombre='%s' AND Apellido1='%s' AND Apellido2='%s'";
    private static String SQL_GET_CAT = "SELECT C.Categoria FROM Corredores C WHERE C.`UCI-ID`='%s'";
    
    public static void banear(String SQL_BAN_UCI) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_BAN_UCI, SQL_BAN_UCI);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    public static void banear(String _nombre, String _ape1, String _ape2) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_BAN_NAME, _nombre, _ape1, _ape2);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

    public static void desbanear(String SQL_BAN_UCI) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_UBAN_UCI, SQL_BAN_UCI);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    public static void desbanear(String _nombre, String _ape1, String _ape2) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_UBAN_NAME, _nombre, _ape1, _ape2);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

    
    
    
    
    public static CategoriaCorredor getCategoria(String _id) {
        Connection con = null;
        CategoriaCorredor respuesta = null;
        try {
            con = ConnectionProvider.getCon();
            respuesta = getCategoria(_id, con);
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con);
            return respuesta;
        }
    }
    public static CategoriaCorredor getCategoria(String _id, Connection _con){
        CategoriaCorredor respuesta = null;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = _con;
            query = String.format(SQL_GET_CAT, _id);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                respuesta =  CategoriaCorredor.valueOf(rs.getString("Categoria"));

            } else {
                respuesta = null;
            }
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(null, ps, rs);
            return respuesta;
        }
    }
    
}
