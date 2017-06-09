package bd;

import categorias.CategoriaArbitro;
import java.util.Objects;

/**
 *
 * @author Diego
 */
public class Arbitro {

    private String nombre;
    private String ape1;
    private String ape2;
    private String uci;
    private int licencia;
    private CategoriaArbitro cat;
    private String email;

        
    public Arbitro() {
    }
   
    
    
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }
    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }
    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getUci() {
        return uci;
    }
    public void setUci(String uci) {
        this.uci = uci;
    }

    public int getLicencia() {
        return licencia;
    }
    public void setLicencia(int licencia) {
        this.licencia = licencia;
    }

    public CategoriaArbitro getCat() {
        return cat;
    }
    public void setCat(CategoriaArbitro cat) {
        this.cat = cat;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.nombre);
        hash = 73 * hash + Objects.hashCode(this.ape1);
        hash = 73 * hash + Objects.hashCode(this.ape2);
        hash = 73 * hash + Objects.hashCode(this.uci);
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
        final Arbitro other = (Arbitro) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.ape1, other.ape1)) {
            return false;
        }
        if (!Objects.equals(this.ape2, other.ape2)) {
            return false;
        }
        if (!Objects.equals(this.uci, other.uci)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Arbitro{" + "nombre=" + nombre + ", ape1=" + ape1 + ", ape2=" + ape2 + ", uci=" + uci + ", licencia=" + licencia + ", cat=" + cat + ", email=" + email + '}';
    }

    
    
    
}
