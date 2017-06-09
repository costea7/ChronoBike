package bd.DAO;

import bd.Arbitro;
import bd.conect.ConnectionProvider;
import categorias.CategoriaArbitro;
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
public class ArbritroDAO {

    private static String SQL_GETBY_UCI = "select *  from Arbitros A where A.`UCI-ID`='%s'";
    private static String SQL_GETBY_NAME = "select *  from Arbitros A where A.Nombre='%s' AND A.Apellido1'%s' AND A.Apellido2='%s'";
    private static int MAX_RESULT = 40;
    private static String SQL_ARBITROS = "select *  from Arbitros A";
    private static String SQL_ARBITROS_CAT = "select *  from Arbitros A WHERE A.Categoria='%s'";
  
        
        
        
    public static Arbitro getArbitro(String _id){
        Arbitro respuesta = null;
        Arbitro aux;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GETBY_UCI, _id);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
            if(rs.next()){
                aux = new Arbitro();
                aux.setUci(_id);
                aux.setNombre(rs.getString("Nombre"));
                aux.setApe1(rs.getString("Apellido1"));
                aux.setApe1(rs.getString("Apellido2"));
                aux.setEmail(rs.getString("Email"));
                aux.setLicencia(rs.getInt("Licencia"));
                aux.setCat(CategoriaArbitro.valueOf(rs.getString("Categoria")));
                
                respuesta = aux;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);  
            return respuesta;
        }
    }
    public static Arbitro getArbitro(String _nombre, String _ap1, String _ap2) {
        Arbitro respuesta = null;
        Arbitro aux;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GETBY_NAME, _nombre, _ap1, _ap2);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            if (rs.next()) {
                aux = new Arbitro();
                aux.setUci(rs.getString("UCI-ID"));
                aux.setNombre(_nombre);
                aux.setApe1(_ap1);
                aux.setApe1(_ap2);
                aux.setEmail(rs.getString("Email"));
                aux.setLicencia(rs.getInt("Licencia"));
                aux.setCat(CategoriaArbitro.valueOf(rs.getString("Categoria")));

                respuesta = aux;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);

            return respuesta;
        }
    }

    
    
    /**
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Arbitro> getArbitros(int firstResult) {
        return getArbitros(null, MAX_RESULT, firstResult);
    }
    /**
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param _categoria indicara el tipo de cateogira a la cual perteneece el 
     *      arbitro.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Arbitro> getArbitros(CategoriaArbitro _categoria, int firstResult) {
        return getArbitros(_categoria, MAX_RESULT, firstResult);
    } 
    /**
     * Llamar bajo responsabilidad de desborde por maxResult.
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param _categoria indicara el tipo de notifiaciones de las que se espera
     * resultado.
     * @param maxResult maximo resultado.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Arbitro> getArbitros(CategoriaArbitro _categoria, int maxResult, int firstResult) {

        if (_categoria == null) {
            return getArbitrosList(maxResult, firstResult);
        } else {
            return getArbitrosList(_categoria, maxResult, firstResult);
        }
    }

    /**
     * 
     * @param maxResults
     * @param firstResult
     * @param _idUser
     * @param _categoria **
     * @return 
     */
    private static List<Arbitro> getArbitrosList(CategoriaArbitro _categoria, int maxResults, int firstResult) {
        List<Arbitro> result = new ArrayList<>();
        Arbitro aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_ARBITROS_CAT, _categoria);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while(rs.next()){
                if (index >= firstResult) {
                    aux = new Arbitro();
                    aux.setUci(rs.getString("UCI-ID"));
                    aux.setNombre(rs.getString("Nombre"));
                    aux.setApe1(rs.getString("Apellido1"));
                    aux.setApe1(rs.getString("Apellido2"));
                    aux.setEmail(rs.getString("Email"));
                    aux.setLicencia(rs.getInt("Licencia"));
                    aux.setCat(_categoria);
                    
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
    /**
     * 
     * @param maxResults
     * @param firstResult
     * @param _idUser
     * @return 
     */
    private static List<Arbitro> getArbitrosList(int maxResults, int firstResult) {
        List<Arbitro> result = new ArrayList<>();
        Arbitro aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_ARBITROS);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                if (index >= firstResult) {
                    aux = new Arbitro();
                    aux.setUci(rs.getString("UCI-ID"));
                    aux.setNombre(rs.getString("Nombre"));
                    aux.setApe1(rs.getString("Apellido1"));
                    aux.setApe1(rs.getString("Apellido2"));
                    aux.setEmail(rs.getString("Email"));
                    aux.setLicencia(rs.getInt("Licencia"));
                    aux.setCat(CategoriaArbitro.valueOf(rs.getString("Categoria")));

                    if (size < maxResults) {
                        result.add(aux);
                    } else {
                        break;
                    }
                } else {
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
}
