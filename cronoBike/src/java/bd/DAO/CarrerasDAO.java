/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.DAO;

import bd.Carrera;
import bd.Relacion;
import bd.conect.ConnectionProvider;
import categorias.CategoriaCorredor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author jabih
 */
public class CarrerasDAO {
    
    private static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private static int MAX_RESULT = 40;
    private static String SQL_CARRERAS = "select *  from Carreras c";
    private static String SQL_GET_CARRERAS = "select *  from Carreras c WHERE idCarrera='%s'";
    private static String SQL_GETCARRERAS_BY_CATEGORIA = "select *  from Carreras c where c.`categoriaPrueba`='%s'";
    private static String SQL_GETCARRERAS_BY_ID = "select *  from Carreras c where c.idCarrera='%s'";
    private static String SQL_INSERT = "INSERT INTO Carreras (nombrePrueba, clubOrganizador, licenciaClub, categoriaPrueba, fechaInicio, horaInicio, fechaLimiteInscripcion, "
            + "lugarCelebracion, numeroParticipantesMaximos) VALUES ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');";
    private static String SQL_GET_ID = "SELECT C.idCarrera FROM Carreras C WHERE C.nombrePrueba='%s'";
    private static String SQL_UPDATE_IDJT = "UPDATE  Carreras SET idJuradoTecnico='%s' WHERE idCarrera='%s'";
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static String SQL_DELETE = "DELETE Carreras FROM Carreras WHERE  idCarrera='%s'";
    private static String SQL_UPDATE = "UPDATE  Carreras SET nombrePrueba='%s', clubOrganizador='%s', licenciaClub='%s', categoriaPrueba='%s', fechaInicio='%s', horaInicio='%s', fechaLimiteInscripcion='%s', lugarCelebracion='%s', observaciones='%s',numeroParticipantesMaximos='%s' WHERE idCarrera='%s'";
     
    private static String SQL_GET_PUNTUACION = "SELECT * FROM Calificaciones WHERE `UCI-ID`='%s'";
    private static String SQL_GET_BY_ID_DATE = "SELECT * FROM Carreras WHERE idCarrera='%s' AND fechaInicio<'%s'";

//----CARRERAS A LAS QUE PUEDE ACCEDER EL CORREDOR ----------------------------
    public static List<Carrera> getCarreras(CategoriaCorredor _categoria, int firstResult) {
        return getCarreras(_categoria, MAX_RESULT, firstResult);
    } 
    public static List<Carrera> getCarreras(CategoriaCorredor _categoria, int maxResult, int firstResult) {

        return getACarrerasList(_categoria, maxResult, firstResult);
    }
    private static List<Carrera> getACarrerasList(CategoriaCorredor _categoria, int maxResults, int firstResult) {
        List<Carrera> result = new ArrayList<>();
        Carrera aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println("Listando...");
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GETCARRERAS_BY_CATEGORIA, _categoria);
            System.out.println("Q: " + query);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
            while(rs.next()){
                if (index >= firstResult) {
                    aux = new Carrera();
                    aux.setIdCarrera(rs.getInt("idCarrera"));
                    aux.setNombrePrueba(rs.getString("nombrePrueba"));
                    aux.setClubOrganizador(rs.getString("clubOrganizador"));
                    aux.setLicenciaClub(rs.getString("licenciaClub"));
                    aux.setCategoriaPrueba(_categoria); 
                    aux.setFechaInicio(formatter.parse(rs.getString("fechaInicio")));
                    aux.setHoraInicio(rs.getString("horaInicio"));
                    aux.setFechaLimiteInscripcion(formatter.parse(rs.getString("fechaLimiteInscripcion")));
                    aux.setLugarCelebracion(rs.getString("lugarCelebracion"));
                    aux.setObservaciones(rs.getString("observaciones"));
                    aux.setNumMaxParticipantes(rs.getInt("numeroParticipantesMaximos"));
                    aux.setIdJuradoTecnico(rs.getString("idJuradoTecnico"));
                    aux.setStatus(0);
                    
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
//-----------------------------------------------------------------------------
//----CARRERAS A LAS QUE EL USUARIO CON UCI ESTA ISNCIRITO
    public static List<Carrera> getCarrerasCorredor(String _idUCI, int firstResult) {
        return getCarrerasCorredor(_idUCI, MAX_RESULT, firstResult);
    } 
    public static List<Carrera> getCarrerasCorredor(String _idUCI, int maxResult, int firstResult) {

        return getCarrerasCorredorList(_idUCI, maxResult, firstResult);
    }
    private static List<Carrera> getCarrerasCorredorList(String _idUCI, int maxResults, int firstResult) {
        List<Carrera> result = new ArrayList<>();
        List<Relacion> auxList = new ArrayList<>();
        Carrera aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            auxList = RelacionesDAO.getCarreras(_idUCI, firstResult);

            con = ConnectionProvider.getCon();
            for (int j = 0; j < auxList.size(); j++) {
                if (index >= firstResult) {
                    query = String.format(SQL_GETCARRERAS_BY_ID, auxList.get(j).getIdCarrera());
                    System.out.println("Carrera inscrita " + auxList.get(j).getIdCarrera());
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    rs.next();
                    
                    aux = new Carrera();
                    aux.setIdCarrera(auxList.get(j).getIdCarrera());
                    aux.setNombrePrueba(rs.getString("nombrePrueba"));
                    aux.setClubOrganizador(rs.getString("clubOrganizador"));
                    aux.setLicenciaClub(rs.getString("licenciaClub"));
                    aux.setCategoriaPrueba(CategoriaCorredor.valueOf(rs.getString("categoriaPrueba")));
                    aux.setFechaInicio(formatter.parse(rs.getString("fechaInicio")));
                    aux.setHoraInicio(rs.getString("horaInicio"));
                    aux.setFechaLimiteInscripcion(formatter.parse(rs.getString("fechaLimiteInscripcion")));
                    aux.setLugarCelebracion(rs.getString("lugarCelebracion"));
                    aux.setObservaciones(rs.getString("observaciones"));
                    aux.setNumMaxParticipantes(rs.getInt("numeroParticipantesMaximos"));
                    aux.setIdJuradoTecnico(rs.getString("idJuradoTecnico"));
                    aux.setStatus(1);

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
//-----------------------------------------------------------------------------
//----CARRERAS A LAS QUE EL ARBITRO CON UCI ESTA ISNCIRITO   
    public static List<Carrera> getCArbitroPendientes(String _idUCI, int firstResult) {
        return getCArbitroListPend(_idUCI, MAX_RESULT, firstResult);
    } 
    public static List<Carrera> getCArbitroPendientes(String _idUCI, int maxResult, int firstResult) {
        return getCArbitroListPend(_idUCI, maxResult, firstResult);
    }
    private static List<Carrera> getCArbitroListPend(String _idUCI, int maxResults, int firstResult) {
        List<Carrera> result = new ArrayList<>();
        List<Relacion> auxList = new ArrayList<>();
        Carrera aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idJurado;

        try {
            auxList = RelacionesDAO.getCarreras(_idUCI, firstResult);

            con = ConnectionProvider.getCon();
            for (int j = 0; j < auxList.size(); j++) {
                if (index >= firstResult) {
                    query = String.format(SQL_GETCARRERAS_BY_ID, auxList.get(j).getIdCarrera());
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    rs.next();
                    
                    aux = new Carrera();
                    aux.setIdCarrera(auxList.get(j).getIdCarrera());
                    aux.setNombrePrueba(rs.getString("nombrePrueba"));
                    aux.setClubOrganizador(rs.getString("clubOrganizador"));
                    aux.setLicenciaClub(rs.getString("licenciaClub"));
                    aux.setCategoriaPrueba(CategoriaCorredor.valueOf(rs.getString("categoriaPrueba")));
                    aux.setFechaInicio(formatter.parse(rs.getString("fechaInicio")));
                    aux.setHoraInicio(rs.getString("horaInicio"));
                    aux.setFechaLimiteInscripcion(formatter.parse(rs.getString("fechaLimiteInscripcion")));
                    aux.setLugarCelebracion(rs.getString("lugarCelebracion"));
                    aux.setObservaciones(rs.getString("observaciones"));
                    aux.setNumMaxParticipantes(rs.getInt("numeroParticipantesMaximos"));
                    idJurado = rs.getString("idJuradoTecnico");
                    aux.setIdJuradoTecnico(idJurado);
                    aux.setStatus(JTecnicoDAO.getEstado(idJurado, _idUCI, con));

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
//-----------------------------------------------------------------------------
    public static List<Carrera> getCArbitroFinalizadas(String _idUCI, int firstResult) {
        return getCArbitroFinalizadas(_idUCI, MAX_RESULT, firstResult);
    }        
    public static List<Carrera> getCArbitroFinalizadas(String _idUCI, int maxResult, int firstResult) {
        return getCArbitroListFin(_idUCI, maxResult, firstResult);
    }   
    private static List<Carrera> getCArbitroListFin(String _idUCI, int maxResults, int firstResult) {
        List<Carrera> result = new ArrayList<>();
        List<Relacion> auxList = new ArrayList<>();
        Carrera aux;
        int index = 0;
        int size = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idJurado;
        Calendar cal = Calendar.getInstance();
        String fechaString = formato.format(cal.getTime());

        try {
            auxList = RelacionesDAO.getCarreras(_idUCI, firstResult);

            con = ConnectionProvider.getCon();
            for (int j = 0; j < auxList.size(); j++) {
                if (index >= firstResult) {
                    query = String.format(SQL_GET_BY_ID_DATE, auxList.get(j).getIdCarrera(), fechaString);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        aux = new Carrera();
                        aux.setIdCarrera(auxList.get(j).getIdCarrera());
                        aux.setNombrePrueba(rs.getString("nombrePrueba"));
                        aux.setClubOrganizador(rs.getString("clubOrganizador"));
                        aux.setLicenciaClub(rs.getString("licenciaClub"));
                        aux.setCategoriaPrueba(CategoriaCorredor.valueOf(rs.getString("categoriaPrueba")));
                        aux.setFechaInicio(formatter.parse(rs.getString("fechaInicio")));
                        aux.setHoraInicio(rs.getString("horaInicio"));
                        aux.setFechaLimiteInscripcion(formatter.parse(rs.getString("fechaLimiteInscripcion")));
                        aux.setLugarCelebracion(rs.getString("lugarCelebracion"));
                        aux.setObservaciones(rs.getString("observaciones"));
                        aux.setNumMaxParticipantes(rs.getInt("numeroParticipantesMaximos"));
                        idJurado = rs.getString("idJuradoTecnico");
                        aux.setIdJuradoTecnico(idJurado);
                        aux.setStatus(JTecnicoDAO.getEstado(idJurado, _idUCI, con));

                        if (size < maxResults) {
                            result.add(aux);
                        } else {
                            break;
                        }
                    } else {
                        index++;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return result;
        }
    }
    
    public static Carrera getInfo(int _id){
        Carrera respuesta = null;
        Carrera aux = null;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_CARRERAS, _id);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            
            if(rs.next()){
                    aux = new Carrera();
                    aux.setIdCarrera(_id);
                    aux.setNombrePrueba(rs.getString("nombrePrueba"));
                    aux.setClubOrganizador(rs.getString("clubOrganizador"));
                    aux.setLicenciaClub(rs.getString("licenciaClub"));
                    aux.setCategoriaPrueba(CategoriaCorredor.valueOf(rs.getString("categoriaPrueba")));
                    aux.setFechaInicio(formatter.parse(rs.getString("fechaInicio")));
                    aux.setHoraInicio(rs.getString("horaInicio"));
                    aux.setFechaLimiteInscripcion(formatter.parse(rs.getString("fechaLimiteInscripcion")));
                    aux.setLugarCelebracion(rs.getString("lugarCelebracion"));
                    aux.setObservaciones(rs.getString("observaciones"));
                    aux.setNumMaxParticipantes(rs.getInt("numeroParticipantesMaximos"));
                    aux.setIdJuradoTecnico(String.valueOf(_id));
                    
                    respuesta = aux;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
    
    
    
//--------------------------------UTILS
   public static boolean insert(Carrera _nueva) {
        boolean respuesta = false;
        int idPrueba;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        //( , , fechaLimiteInscripcion, lugarCelebracion, numeroParticipantesMaximos)
        try {
            con = ConnectionProvider.getCon();

            query = String.format(SQL_INSERT, _nueva.getNombrePrueba(), _nueva.getClubOrganizador(),
                    _nueva.getLicenciaClub(), _nueva.getCategoriaPrueba(), _nueva.fechaInicioString(),
                    _nueva.getHoraInicio(), _nueva.fechaLIString(),
                    _nueva.getLugarCelebracion(), _nueva.getNumMaxParticipantes());
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            idPrueba = getId(_nueva.getNombrePrueba(), con);
            
            query = String.format(SQL_UPDATE_IDJT, idPrueba, idPrueba);
            ps2 = con.prepareStatement(query);
            ps2.executeUpdate();
        } catch (SQLException ex) {
            respuesta = false;
//            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(null, ps2);
            ConnectionProvider.closeCon(con, ps);
            return respuesta;
        }
    }
   public static int getId(String _nombreCarrera) {
        Connection con = null;
        int respuesta = -1;
        try {
            con = ConnectionProvider.getCon();
            respuesta = getId(_nombreCarrera, con);
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con);
            return respuesta;
        }
    }
   public static int getId(String _nombreCarrera, Connection _con) {
        int respuesta = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = _con;
            query = String.format(SQL_GET_ID, _nombreCarrera);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                respuesta = rs.getInt("idCarrera");

            } else {
                respuesta = 1;
            }
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(null, ps, rs);
            return respuesta;
        }
    }
    
   public static void deleteCarrera(int _idCarrera) {
        String query;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_DELETE, _idCarrera);
            ps = con.prepareStatement(query);

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
        }
    }

   public static boolean updateCarrera(Carrera _nueva) {
        boolean respuesta = false;
        int idPrueba;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        //( , , fechaLimiteInscripcion, lugarCelebracion, numeroParticipantesMaximos)
        try {
            con = ConnectionProvider.getCon();
//, , ', , , ', ', , observaciones='%s' WHERE idCarrera='%s'";
            query = String.format(SQL_UPDATE, _nueva.getNombrePrueba(), _nueva.getClubOrganizador(),
                    _nueva.getLicenciaClub(), _nueva.getCategoriaPrueba(), _nueva.fechaInicioString(),
                    _nueva.getHoraInicio(), _nueva.fechaLIString(),
                    _nueva.getLugarCelebracion(),_nueva.getObservaciones(),
                    _nueva.getNumMaxParticipantes(), _nueva.getIdCarrera());
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException ex) {
            respuesta = false;
//            ex.printStackTrace();
        } finally {
            ConnectionProvider.closeCon(con, ps);
            return respuesta;
        }
    }
    
   
   public static int getPuntuacion(String _idCorredor) {       
        int respuesta = 0;
        String query;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionProvider.getCon();
            query = String.format(SQL_GET_PUNTUACION, _idCorredor);
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            if (rs.next()) {
                respuesta = rs.getInt("");

            } 
        } catch (Exception e) {
        } finally {
            ConnectionProvider.closeCon(con, ps, rs);
            return respuesta;
        }
    }
    

}
