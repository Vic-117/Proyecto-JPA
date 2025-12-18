/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author digis
 */
public class Direccion {

    private int IdDireccion;
    @NotNull(message = "Escribe tu calle")
    @NotEmpty(message = "Escribe tu calle")
    @Size(min = 2, max = 49)
    private String calle;
    @Size(min = 1, max = 49)
    @NotNull(message = "Escribe tu numero interior")
    @NotEmpty(message = "Escribe tu numero interior")
    private String numeroInterior;
    @Size(min = 1, max = 49)
    @NotNull(message = "Escribe tu numero exterior")
    @NotEmpty(message = "Escribe tu numero exterior")
    private String numeroExterior;
    @NotNull(message = "Ingresa una direccion")
    public Colonia colonia;
    
// public Usuario usuario;//RELACION DEL LADO DE MUCHOS,Muchas direcciones son parte de un Usuario

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.IdDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior() {
        return numeroExterior;

    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

}
