package bd.DAO;

import bd.ArbitroJ;
import bd.conect.ConnectionProvider;
import categorias.FuncionArbitro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * WIP
 * @author Diego
 */
public class JTecnicoDAO {
    /**
     * Get Gurado Tenico (_idjurado)
     * 
     */
    
    private static String SQL_DELETE_ROW = "DELETE JuradoTecnico FROM JuradoTecnico J WHERE J.IdJurado='%s' AND J.IdArbitro='%s'";
    private static String SQL_DELETE_ROW_ROL = "DELETE JuradoTecnico FROM JuradoTecnico J WHERE J.IdJurado='%s' AND J.Rol='%s'";
    private static String SQL_DELETE_GROUP = "DELETE JuradoTecnico FROM JuradoTecnico J WHERE J.IdJurado='%s'";
    private static String SQL_INSERT = "INSERT INTO JuradoTecnico (IdJurado, IdArbitro, Rol, Estado) VALUES ( '%s', '%s', '%s', '%s');";
    private static String SQL_UPDATE_ESTADO = "UPDATE  JuradoTecnico SET Estado='%s' WHERE IdJurado='%s' AND IdArbitro='%s' AND Rol='%s'"; 
    private static String SQL_GET_GROUP = "SELECT J.IdArbitro, J.Rol, J.Estado FROM JuradoTecnico J WHERE J.IdJurado='%s';";
    private static String SQL_GET_STATUS = "SELECT J.Estado FROM JuradoTecnico J WHERE J.IdJurado='%s' AND J.IdArbitro='%s'";
    
    public static void asignar(int _idJurado, String _idArbitro, FuncionArbitro _rol){
        
        deleteAJ(_idJurado, _idArbitro);
        if( !(_rol.equals(FuncionArbitro.COMISARIO_EN_FORMACION)) && !(_rol.equals(FuncionArbitro.COMISARIO_EN_MOTO))){
            deleteAJ(_idJurado, _rol);
        }
        
        insertAJ(_idJurado, _idArbitro, _rol);
    }
    /**
     * Elimina al Arbitro de la asignacion de jurado tecnico.
     * 
     * @param _idJurado
     * @param _idArbitro 
     */
    private static void deleteAJ(int _idJurado, String _idArbitro){
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE_ROW, _idJurado, _idArbitro);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    private static void deleteAJ(int _idJurado, FuncionArbitro _rol) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE_ROW_ROL, _idJurado, _rol);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    private static void insertAJ(int _idJurado, String _idArbritro, FuncionArbitro _rol) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_INSERT, _idJurado, _idArbritro, _rol.toString(), '0');
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

    /**
     * Borra todas las asignaciones asociadas a ese Jurado.
     * 
     * @param _idJurado 
     */
    public static void deleteJurado(int _idJurado) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE_GROUP, _idJurado);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }
    
    /**
     * PRE: El Arbitro esta asignado a esa carrera y ese rol.
     * 
     * @param _idJurado
     * @param _idArbitro
     * @param _rol
     * @param _estado 
     */
    public static void responderAsignacion(String _idJurado, String _idArbitro, FuncionArbitro _rol, int _estado) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_UPDATE_ESTADO, _estado, _idJurado, _idArbitro, _rol);
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

    public static List<ArbitroJ> getJuradoTecnico(String _idJurado) {
        List<ArbitroJ> result = new ArrayList<>();
        ArbitroJ aux;
        int index = 0;
        int size = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_GROUP, _idJurado);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                aux = new ArbitroJ();
                aux.setId(rs.getInt("IdArbitro"));
                aux.setEstado(rs.getInt("Estado"));
                aux.setRol(FuncionArbitro.valueOf(rs.getString("Rol")));
                
                result.add(aux);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return result;
        }
    }



   public static int getEstado(String _idJurado, String _idArbitro) {
        Connection con = null;
        int respuesta = -1;
        try {
            con = ConnectionProvider.getCon();
            respuesta = getEstado(_idJurado, _idArbitro, con);
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con);
            return respuesta;
        }
    }
   public static int getEstado(String _idJurado, String _idArbitro, Connection _con) {       
        int respuesta = -1;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = _con;
            query = String.format(SQL_GET_STATUS, _idJurado, _idArbitro);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                respuesta = rs.getInt("Califiacion");

            } else {
                respuesta = 1;
            }
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(null, ps, rs);
            return respuesta;
        }
    }
   
   
    
    
}
