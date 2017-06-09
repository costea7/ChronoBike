package bd.bean;


import bd.ArbitroJ;
import bd.Carrera;
import bd.DAO.CarrerasDAO;
import bd.DAO.JTecnicoDAO;
import bd.Notificacion;
import bd.DAO.NotificacionDAO;
import bd.DAO.RelacionesDAO;
import bd.Relacion;
import categorias.CategoriaCorredor;
import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author Diego
 */
public class BeanCarrera implements Serializable { 

    
    public BeanCarrera() {
    }
    
   
    
    /**
     *
     * @param _id id del usuario del que se quieren las notificaciones.
     * @param _index nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public List<Carrera> getCarreras(CategoriaCorredor _categoria, int _index) {
        List<Carrera> models = new ArrayList<>();
        
        models = CarrerasDAO.getCarreras(_categoria, _index);

        return models;
    }
    
    public List<Carrera> getCarrerasCorredor(String _id_uci, int _index) {
        
        List<Carrera> models = new ArrayList<>();
        
        models = CarrerasDAO.getCarrerasCorredor(_id_uci, _index);

        return models;
    }
    
     public List<Carrera> getCArbitroPendientes(String _id_uci, int _index) {
        List<Carrera> models = new ArrayList<>();
        
        models = CarrerasDAO.getCArbitroPendientes(_id_uci, _index);

        return models;
    }
     
    public List<Carrera> getCArbitroFin(String _id_uci, int _index) {
        List<Carrera> models = new ArrayList<>();

        models = CarrerasDAO.getCArbitroFinalizadas(_id_uci, _index);

        return models;
    }
     
     
     
     public void inscribirse(int _idCarrera, String _idUser){
         RelacionesDAO.insertRow(_idCarrera, _idUser);
     }
     
     public int getPuntuacion(String _idCorredor){
         return CarrerasDAO.getPuntuacion(_idCorredor);
     }
     
     public Carrera getCarrera(int _id){
         return CarrerasDAO.getInfo(_id);
     }
     
    public int getNumParticipantes(int _id) {
        int respuesta = 0;

        System.out.println("dameeee " + _id); 
       
        List<ArbitroJ> aux2;
        respuesta = RelacionesDAO.getRelacionados(String.valueOf(_id));

        aux2 = JTecnicoDAO.getJuradoTecnico(String.valueOf(_id));
        System.out.println("REL " + respuesta);
        if (aux2 != null) {
            respuesta -= aux2.size();
            System.out.println("JT " + aux2.size());


        }
        return respuesta;
    }
}
