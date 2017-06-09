
package bd;

import java.util.Objects;

/**
 *
 * @author Diego
 */
public class Relacion {
    private int id;
    private int idCarrera;
    private String uci;

    public Relacion() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
    
    public String getUci() {
        return uci;
    }
    public void setUci(String uci) {
        this.uci = uci;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relacion other = (Relacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Relacion{" + "id=" + id + ", idCarrera=" + idCarrera + ", uci=" + uci + '}';
    }
    
    
    
}
