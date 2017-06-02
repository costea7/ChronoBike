/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author Diego
 */
public class NotificacionesJpaController {

    public NotificacionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    
    private static String SQL_NUMNEWS = "select u.Id from Notificaciones u where u.Destinatario='%s'";
    /**
     * Metodo que devuelve el numero de notificaciones nuevas.
     * 
     */
    public int getNews(String _id) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery(String.format(SQL_NUMNEWS, _id));

            List<Object[]> usuarios = q.getResultList();
            
            return usuarios.size();
        } finally {
            em.close();
        }
    }
    
//    public List<Notificaciones> getNotificationAll() {
//        return findPersonaEntities(true, -1, -1);
//    }
//
//    public List<Notificaciones> findPersonaEntities(int maxResults, int firstResult) {
//        return findPersonaEntities(false, maxResults, firstResult);
//    }

    private static String SQL_NOTIFICATION = "select *  from Notificaciones where Notificaciones.Destinatario='%s'";
    public List<Notificaciones> getNotifications(boolean all, int maxResults, int firstResult, String _id) {
        EntityManager em = getEntityManager();
        List<Notificaciones> result = new ArrayList<>();
        try {
            Query q = em.createNativeQuery(String.format(SQL_NOTIFICATION, _id), Notificaciones.class);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }
    
    
    
}
