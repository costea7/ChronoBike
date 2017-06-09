package bd.DAO;

import bd.Relacion;
import bd.conect.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class RelacionesDAO {
    /**
     *  Lista de Carreras relacionadas con arbitro/corredor
     * 
     *  [AI] [IDCarrera] [UCI]
     * 
     */
        private static int MAX_RESULT = 40;
    public static int getRelacionados(String _idCarrera){
        int respuesta = 0;
        List<Relacion> aux = new ArrayList<>();
        Relacion aux2;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_REL, _idCarrera);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                aux2 = new Relacion();
                aux2.setId(rs.getInt("id"));
                aux2.setIdCarrera(rs.getInt("idCarrera"));
                aux2.setUci(rs.getString("UCI"));

                aux.add(aux2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            respuesta = aux.size();
            return respuesta;
        }
    }

    private static String SQL_GET_REL = "select *  from Relacion R where R.idCarrera='%s'";
    private static String SQL_GET_LIST = "select *  from Relacion R where R.UCI='%s'";
    private static String SQL_INSERT = "INSERT INTO Relacion (idCarrera, UCI) VALUES ( '%s', '%s');";
    private static String SQL_DELETE_ROWS = "DELETE Relacion FROM Relacion R WHERE R.idCarrera='%s';";
    private static String SQL_DELETE_ROW = "DELETE  FROM Relacion WHERE idCarrera='%s' AND UCI='%s';";
    
    /**
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Relacion> getCarreras(String _idUser, int firstResult) {
        return getCarreras(_idUser, MAX_RESULT, firstResult);
    }
    /**
     * Llamar bajo responsabilidad de desborde por maxResult.
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param maxResult maximo resultado.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Relacion> getCarreras(String _idUser, int maxResult, int firstResult) {


            return getCarrerasList(_idUser, maxResult, firstResult);
        
    }
    /**
     * 
     * @param maxResults
     * @param firstResult
     * @param _idUser
     * @param _categoria **
     * @return 
     */
    private static List<Relacion> getCarrerasList(String _idUser, int maxResults, int firstResult) {
        List<Relacion> result = new ArrayList<>();
        Relacion aux;
        int index = 0;
        int size = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_LIST, _idUser);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while(rs.next()){
                if (index >= firstResult) {
                    aux = new Relacion();
                    aux.setId(rs.getInt("id"));
                    aux.setIdCarrera(rs.getInt("idCarrera"));
                    aux.setUci(rs.getString("UCI"));
                    
                    if(size < maxResults){
                      result.add(aux);  
                    }else{
                        break;
                    }
                }else{
                    index++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return result;
        }
    }

    public static void insertRow(int _idCarrera, String _iduser){
        String query;
        Connection con = null;
        PreparedStatement ps = null;
       
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_INSERT, _idCarrera, _iduser);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    
    public static void deleteRows(int _idCarrera) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE_ROWS, _idCarrera);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    
    public static void deleteRow(int _idCarrera, String _iduser){
                String query;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE_ROW, _idCarrera, _iduser);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

    public static void insertRow(String idCarrera, String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
