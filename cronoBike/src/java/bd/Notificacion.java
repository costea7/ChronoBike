package bd;

import categorias.CategoriaNotificacion;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Diego
 */
public class Notificacion implements Serializable {

    private int id;
    private String destinatario;
    private String texto;
    private Date fecha;
    private CategoriaNotificacion tipo;


    public Notificacion() {
        this.id = -1;
    }
    public Notificacion(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.destinatario);
        hash = 19 * hash + Objects.hashCode(this.texto);
        hash = 19 * hash + Objects.hashCode(this.fecha);
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
        final Notificacion other = (Notificacion) obj;
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Notification[ id=" + id + " ]";
    }
    
    
    
    public String getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String fechaString(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(fecha);
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CategoriaNotificacion getTipo() {
        return tipo;
    }
    public void setTipo(CategoriaNotificacion tipo) {
        this.tipo = tipo;
    }
    public String getTipoString(){
        return tipo.toString();
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
}
