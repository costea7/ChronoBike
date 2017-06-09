package bd.bean;


import bd.Carrera;
import bd.DAO.CarrerasDAO;
import bd.DAO.CorredorDAO;
import bd.Notificacion;
import bd.DAO.NotificacionDAO;
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
public class BeanCorredor implements Serializable { 

    
    public BeanCorredor() {
    }
    
   
    
    /**
     *
     * @param _id id del usuario del que se quieren las notificaciones.
     * @param _index nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public  CategoriaCorredor getCategoria(String _id) {

        return CorredorDAO.getCategoria(_id);
    }
    
  
}
