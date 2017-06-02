/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import bd.exceptions.NonexistentEntityException;
import bd.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import categorias.Rol;

/**
 *
 * @author alumno
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario _user) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(_user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario _user) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            _user = em.merge(_user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = _user.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String _id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario user;
            try {
                user = em.getReference(Usuario.class, _id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + _id + " no longer exists.", enfe);
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Usuario> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findPersona(String _id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, _id);
        } finally {
            em.close();
        }
    }
/**
 *             Query q = em.createNativeQuery("select u.Id, u.Rol from Usuarios u");
            List<Object[]> usuarios = q.getResultList();
            
            for(Object[] u : usuarios){
                System.out.println(">>Uuser: " + u[0]);
                System.out.println(">>Rool: " + u[1]);
            }
 * @return 
 */
    private static String SQL_ISBANNED = "select u.Rol from Usuarios u where u.Id='%s' AND u.Pass='%s'";
    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Usuario> rt = cq.from(Usuario.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
            //String _user = "a";
            //String a = em.createQuery("select u.Rol from Usuarios u where Id=:ID and Pass='a'").setParameter("ID", _user);
            String pam1 = "a";
            String pam2 = "a";
            Query q = em.createNativeQuery(String.format(SQL_ISBANNED, pam1, pam2));
            //q = q.setParameter("pam1", "a");
            System.out.println(">>Uuser: " + q.toString());
            System.out.println(">>Uuser: " + q.getSingleResult());
//            List<Object[]> usuarios = q.getResultList();
//            
//            for(Object[] u : usuarios){
//                System.out.println(">>Uuser: " + u[0]);
//                System.out.println(">>Rool: " + u[1]);
//            }
            
            //System.out.println(">>>>>>>: " + a);

            return 1;
        } finally {
            em.close();
        }
    }
        
    private static String SQL_ROL_IDPAS = "select u.Rol from Usuarios u where u.Id='%s' AND u.Pass='%s'";
    public int validar(Usuario _user){
        EntityManager em = getEntityManager();
        int respuesta = 0;
        try {
            String pam1 = _user.getId();
            String pam2 = _user.getPass();
            Query q = em.createNativeQuery(String.format(SQL_ISBANNED, pam1, pam2));
            //q = q.setParameter("pam1", "a");
            System.out.println(">>Uuser: " + q.toString());
            System.out.println(">>Uuser: " + q.getSingleResult());
            String rol = (String) q.getSingleResult();
            if(rol!=null){
                Rol rolEnum = Rol.valueOf(rol);
                switch (rolEnum) {
                    case ADMINISTRADOR:
                        respuesta = 4;
                        break;
                    case CORREDOR:
                        respuesta = 3;
                        break;
                    case ARBITRO:
                        respuesta = 2;
                        break;
                    default:
                        respuesta = 5;
                }

            } else {
                respuesta = 1;
            }
        } catch (Exception e) {
        } finally {
            System.out.println("++: " + respuesta);
            return respuesta;
        }
    }

    private static String SQL_GETUCI = "select u.`UCI-ID` from Usuarios u where u.Id='a' AND u.Pass='a'";
    public  String getUCI(Usuario _user){
        EntityManager em = getEntityManager();
        String respuesta = "";
        String pam1;
        String pam2;
        Query q;
        String uci;
        
        try {
            pam1 = _user.getId();
            pam2 = _user.getPass();
            q = em.createNativeQuery(String.format(SQL_GETUCI, pam1, pam2));

            uci = (String) q.getSingleResult();
            if(uci!=null){
               respuesta = uci;
            } else {
                respuesta = null;
            }
        } catch (Exception e) {
        } finally {
            return respuesta;
        }
    }
}
