package bd.conect;  
import java.sql.*;  
import static bean.Provider.*;  
import java.util.logging.Level;
import java.util.logging.Logger;
  
public class ConnectionProvider {

    private static Connection con = null;

    static {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
        }
    }

    public static Connection getCon() {
        return con;
    }
    
    
    
    public static void closeCon(Connection _con){
        closeCon(_con, null, null);
    }
    public static void closeCon(Connection _con, PreparedStatement _ps) {
        closeCon(_con, _ps, null);
    }
    public static void closeCon(Connection _con, PreparedStatement _ps, ResultSet _rs){
        if(_rs!=null){
            try {
                _rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //----------------
        if(_ps!=null){
            try {
                _ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
//        if(_con!=null){
//            try {
//                _con.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ConnectionProvider.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

}
