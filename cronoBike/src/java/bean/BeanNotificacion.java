package bean;

import bd.Notificaciones;
import bd.NotificacionesJpaController;
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
    private NotificacionesJpaController jpa;
    private Context ctx;
    private UserTransaction utx;
    private EntityManager em;

    
    public BeanNotificacion() {
        try {
            ctx = new InitialContext();
            utx = (UserTransaction) ctx.lookup("java:comp/env/UserTransaction");
            em = (EntityManager) ctx.lookup("java:comp/env/persistence/LogicalName");

            jpa = new NotificacionesJpaController(utx, em.getEntityManagerFactory());

        } catch (NamingException ex) {
        }
    }
    
    public int getNumnews(String _id){
        return jpa.getNews(_id);
    }
    
    public List<Notificaciones> getNotifications(String _id, String _cat){
        List<Notificaciones> models = new ArrayList<>();
        Notificaciones n = null;
        models = jpa.getNotifications(true, -1, 25, _id);

        
//        for(int i = 0; i < models.size(); i++) {
//            n = (Notificaciones) models.get(i);
//            System.out.println(n.toString());
//        }

        return models;
    }
}
