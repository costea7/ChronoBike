/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import categorias.CategoriaCorredor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author jabih
 */
public class Carrera {

    private int idCarrera;
    private String nombrePrueba;
    private String clubOrganizador;
    private String licenciaClub;
    private CategoriaCorredor categoriaPrueba;
    private Date fechaInicio;
    private String horaInicio;
    private Date fechaLimiteInscripcion;
    private String lugarCelebracion;
    private String observaciones;
    private int numMaxParticipantes;
    private String idJuradoTecnico;
    /**
     * 0 - No inscrito
     * 1 - Asignado (Aceptado) [Arbitro]
     * 1 - Inscrito [Corredor]
     * 2 - Pendiente [Arbitro]
     */
    private int status;
    


    
    
    public Carrera() {

    }

    public int getIdCarrera() {
        return idCarrera;
    }
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
   
    public String getNombrePrueba() {
        return nombrePrueba;
    }
    public void setNombrePrueba(String nombrePrueba) {
        this.nombrePrueba = nombrePrueba;
    }

    public String getClubOrganizador() {
        return clubOrganizador;
    }
    public void setClubOrganizador(String clubOrganizador) {
        this.clubOrganizador = clubOrganizador;
    }

    public String getLicenciaClub() {
        return licenciaClub;
    }
    public void setLicenciaClub(String licenciaClub) {
        this.licenciaClub = licenciaClub;
    }

    public CategoriaCorredor getCategoriaPrueba() {
        return categoriaPrueba;
    }
    public void setCategoriaPrueba(CategoriaCorredor categoriaPrueba) {
        this.categoriaPrueba = categoriaPrueba;
    }
    public String getCategoriaPruebaString(){
        return categoriaPrueba.toString();
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String fechaInicioString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fechaInicio);
    }

    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getFechaLimiteInscripcion() {
        return fechaLimiteInscripcion;
    }
    public void setFechaLimiteInscripcion(Date fechaLimiteInscripcion) {
        this.fechaLimiteInscripcion = fechaLimiteInscripcion;
    }
    public String fechaLIString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fechaInicio);
    }

    
    
    public String getLugarCelebracion() {
        return lugarCelebracion;
    }
    public void setLugarCelebracion(String lugarCelebracion) {
        this.lugarCelebracion = lugarCelebracion;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getNumMaxParticipantes() {
        return numMaxParticipantes;
    }
    public void setNumMaxParticipantes(int numMaxParticipantes) {
        this.numMaxParticipantes = numMaxParticipantes;
    }

    public String getIdJuradoTecnico() {
        return idJuradoTecnico;
    }
    public void setIdJuradoTecnico(String idJuradoTecnico) {
        this.idJuradoTecnico = idJuradoTecnico;
    }
    
    /**
     * 0 - No inscrito
     * 1 - Asignado (Aceptado) [Arbitro]
     * 1 - Inscrito [Corredor]
     * 2 - Pendiente [Arbitro]
     */
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Carrera{" + "idCarrera=" + idCarrera + ", nombrePrueba=" + nombrePrueba + ", clubOrganizador=" + clubOrganizador + ", licenciaClub=" + licenciaClub + ", categoriaPrueba=" + categoriaPrueba + ", fechaInicio=" + fechaInicio + ", horaInicio=" + horaInicio + ", fechaLimiteInscripcion=" + fechaLimiteInscripcion + ", lugarCelebracion=" + lugarCelebracion + ", observaciones=" + observaciones + ", numMaxParticipantes=" + numMaxParticipantes + ", idJuradoTecnico=" + idJuradoTecnico + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.idCarrera;
        hash = 61 * hash + Objects.hashCode(this.nombrePrueba);
        hash = 61 * hash + Objects.hashCode(this.categoriaPrueba);
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
        final Carrera other = (Carrera) obj;
        if (this.idCarrera != other.idCarrera) {
            return false;
        }
        if (!Objects.equals(this.nombrePrueba, other.nombrePrueba)) {
            return false;
        }
        if (!Objects.equals(this.clubOrganizador, other.clubOrganizador)) {
            return false;
        }
        if (this.categoriaPrueba != other.categoriaPrueba) {
            return false;
        }
        return true;
    }
    
}
