/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bd.Usuario;
import bd.UsuarioJpaController;
import java.beans.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

/**
 *
 * @author Diego
 */
public class BeanUsuarios implements Serializable {
    private UsuarioJpaController jpa;
    private Context ctx;
    private UserTransaction utx;
    private EntityManager em;
//    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
//    private String sampleProperty;
//    private PropertyChangeSupport propertySupport;
    
    public BeanUsuarios() {
        try {
            ctx = new InitialContext();
            utx = (UserTransaction) ctx.lookup("java:comp/env/UserTransaction");
            em = (EntityManager) ctx.lookup("java:comp/env/persistence/LogicalName");

            jpa = new UsuarioJpaController(utx, em.getEntityManagerFactory());

        } catch (NamingException ex) {
        }
    }
    
//    public String getSampleProperty() {
//        return sampleProperty;
//    }
//    
//    public void setSampleProperty(String value) {
//        String oldValue = sampleProperty;
//        sampleProperty = value;
//        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
//    }
//    
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        propertySupport.addPropertyChangeListener(listener);
//    }
//    
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        propertySupport.removePropertyChangeListener(listener);
//    }
    public Collection<Usuario> getUsers() {
        Collection<Usuario> personas = new ArrayList();

        Collection<Usuario> pppp = jpa.findPersonaEntities();
        for (Usuario p : pppp) {
            personas.add(p);
        }

        return personas;
    }
    
    
    public int getNumUser(){
        return jpa.getPersonaCount();
    }
    
}
