package bd.DAO;

import bd.Notificacion;
import bd.conect.ConnectionProvider;
import categorias.CategoriaNotificacion;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Diego
 */
public class NotificacionDAO {
    private static String SQL_NUMNEWS = "select u.ID from NOTIFICATION u where u.DESTINATARIO='%s'";
    private static String SQL_NOTIFICATION = "select *  from NOTIFICATION N where N.DESTINATARIO='%s'";
    private static String SQL_NOTIFICATION_CAT = "select *  from NOTIFICATION  N where N.DESTINATARIO='%s' AND N.TIPO='%s'";
    private static int MAX_RESULT = 40;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private static String SQL_INSERT = "INSERT INTO NOTIFICATION (DESTINATARIO, FECHA, TEXTO, TIPO) VALUES ( '%s', '%s', '%s', '%s');";

    
        
    /**
     * Metodo que devuelve el numero de notificaciones nuevas.
     */
    public static int getNumNews(String _id){
        Connection con  = null;
        PreparedStatement ps = null;
        String query;
        int respuesta = -1;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_NUMNEWS, _id);
            ps = con.prepareStatement(query);

            
            ResultSet rs = ps.executeQuery();
            do{
                respuesta++;
            }while(rs.next());
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con, ps);
            return respuesta;
        }
    }



    /**
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Notificacion> getNotificationes(String _idUser, int firstResult) {
        return getNotificationes(_idUser, null, MAX_RESULT, firstResult);
    }
    /**
     *
     * @param _idUser id del usuario del que se quieren las notificaciones.
     * @param _categoria indicara el tipo de notifiaciones de las que se espera
     * resultado.
     * @param firstResult nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public static List<Notificacion> getNotificationes(String _idUser, CategoriaNotificacion _categoria, int firstResult) {
        return getNotificationes(_idUser, _categoria, MAX_RESULT, firstResult);
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
    public static List<Notificacion> getNotificationes(String _idUser, CategoriaNotificacion _categoria, int maxResult, int firstResult) {

        if (_categoria == null) {
            return getNotifList(_idUser, maxResult, firstResult);
        } else {
            return getNotifList(_idUser, _categoria, maxResult, firstResult);
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
    private static List<Notificacion> getNotifList(String _idUser, CategoriaNotificacion _categoria, int maxResults, int firstResult) {
        List<Notificacion> result = new ArrayList<>();
        Notificacion aux;
        int index = 0;
        int size = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_NOTIFICATION_CAT, _idUser, _categoria);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while(rs.next()){
                if (index >= firstResult) {
                    aux = new Notificacion();
                    aux.setDestinatario(rs.getString("DESTINATARIO"));
                    aux.setTexto(rs.getString("TEXTO"));
                    aux.setTipo(CategoriaNotificacion.valueOf(rs.getString("TIPO")));
                    aux.setFecha(formatter.parse(rs.getString("FECHA")));
                    
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
        }catch(ParseException p){
            p.printStackTrace();
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
    private static List<Notificacion> getNotifList(String _idUser, int maxResults, int firstResult) {
        List<Notificacion> result = new ArrayList<>();
        Notificacion aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_NOTIFICATION, _idUser);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
            while(rs.next()){
                if (index >= firstResult) {
                    aux = new Notificacion();
                    aux.setDestinatario(rs.getString("DESTINATARIO"));
                    aux.setTexto(rs.getString("TEXTO"));
                    aux.setTipo(CategoriaNotificacion.valueOf(rs.getString("TIPO")));
                    aux.setFecha(formatter.parse(rs.getString("FECHA")));
                    
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
        } catch (ParseException p) {
            p.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return result;
        }
    }


    /**
     * Inserta una notifiacion en la tabla, cuya marca temporal sera el dia actual.
     * 
     * @param _id
     * @param _cat
     * @param _texto
     * @return 
     */
    public static boolean insertNotification(String _id, CategoriaNotificacion _cat, String _texto){
        boolean respuesta = false;
        String query;
        Calendar cal = Calendar.getInstance();
        Connection con = null;
        PreparedStatement ps = null;
        
        String fechaString = formatter.format(cal.getTime());
        try {
            con = ConnectionProvider.getCon();
            
            query = String.format(SQL_INSERT, _id, fechaString, _texto, _cat.toString());
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
            return respuesta;
        }
    }
}
