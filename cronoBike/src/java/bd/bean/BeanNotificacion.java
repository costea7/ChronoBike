package bd.bean;


import bd.Notificacion;
import bd.DAO.NotificacionDAO;
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
public class BeanNotificacion implements Serializable { 

    
    public BeanNotificacion() {
    }
    
    
    
    public int getNumnews(String _id) {
        System.out.println("ID1 " + _id);
        return NotificacionDAO.getNumNews(_id);
    }
    
    /**
     *
     * @param _id id del usuario del que se quieren las notificaciones.
     * @param _index nº de notificacion desde la que se quiere relsultados
     * @return
     */
    public List<Notificacion> getNotificaciones(String _id, int _index) {
        List<Notificacion> models = new ArrayList<>();
        
        models = NotificacionDAO.getNotificationes(_id, _index);

        return models;
    }
}
