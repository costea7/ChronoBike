/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jabih
 */
@Entity
public class SancionPrueba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String licencia;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int numArticuloInfraccion;
    private int cantidadMulta;
    private String observaciones;
    private int numDiasSancion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getNumArticuloInfraccion() {
        return numArticuloInfraccion;
    }

    public void setNumArticuloInfraccion(int numArticuloInfraccion) {
        this.numArticuloInfraccion = numArticuloInfraccion;
    }

    public int getCantidadMulta() {
        return cantidadMulta;
    }

    public void setCantidadMulta(int cantidadMulta) {
        this.cantidadMulta = cantidadMulta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getNumDiasSancion() {
        return numDiasSancion;
    }

    public void setNumDiasSancion(int numDiasSancion) {
        this.numDiasSancion = numDiasSancion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SancionPrueba)) {
            return false;
        }
        SancionPrueba other = (SancionPrueba) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bd.SancionPrueba[ id=" + id + " ]";
    }

}
