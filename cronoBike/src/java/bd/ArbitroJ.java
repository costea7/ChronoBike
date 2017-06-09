package bd;

import categorias.FuncionArbitro;
import java.util.Objects;

/**
 * Objeto Arbitro utilizado para el Jurado Tecnico
 * 
 * @author Diego
 */
public class ArbitroJ {
    /* ID arbitro */
    private int id;
    
    private FuncionArbitro rol;
    /**
     * 0 - Sin asignar
     * 1 - Asignado
     * 2 - Pendiente
     */
    private int estado;
    private String idCarrera;
    
    public ArbitroJ() {
    }

    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public FuncionArbitro getRol() {
        return rol;
    }
    public void setRol(FuncionArbitro rol) {
        this.rol = rol;
    }
    
    /**
     * 0 - Sin asignar 
     * 1 - Asignado
     * 2 - Pendiente
     */
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final ArbitroJ other = (ArbitroJ) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArbitroJ{" + "id=" + id + ", funcion=" + rol + ", estado=" + estado + '}';
    }
}
