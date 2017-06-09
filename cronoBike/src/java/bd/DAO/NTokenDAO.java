package bd.DAO;

import bd.conect.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Diego
 */
public class NTokenDAO {
    private static String SQL_GET_TOKEN = "SELECT * FROM NOTIFIT N WHERE N.ID='%s'";
    private static String SQL_INSERT = "INSERT INTO NOTIFIT (ID, TOKEN) VALUES ( '%s', '%s' );";
    private static String SQL_UPDATE = "UPDATE  NOTIFIT SET TOKEN='%s' WHERE ID='%s'";
    
    
    
    public static String getToken(String _id){
        String respuesta = "";
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_TOKEN, _id);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if(rs.next()){
                respuesta = rs.getString("TOKEN");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
    
    public static void insertOrUpdate(String _id, String _token) {
        if ((_id != null) && (_token != null)) {
            System.out.println("Id: " + _id + " ,Token: " + _token);

            if (isOnTable(_id)) {
                System.out.println("UPDATE----");
                //update
                update(_id, _token);
            } else {
                System.out.println("INSERT----");
                //insert
                insert(_id, _token);
            }
        }
    }
    
    private static boolean isOnTable(String _id) {
        boolean respuesta = false;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_TOKEN, _id);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if(rs.next()){
                respuesta = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
    private static void insert(String _id, String _token) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_INSERT, _id, _token);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    private static void update(String _id, String _token) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_UPDATE, _token, _id);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
}
