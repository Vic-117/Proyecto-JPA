/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.ML;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author digis
 */
public class Colonia {

    private int idColonia;
    @NotNull(message = "Falta la colonia")
    @Size(min=1, max = 49)
    private String nombre;
    @NotNull(message = "Falta el cp")
    private String codigoPostal;
//    Municipio municipio = new Municipio();
    @NotNull(message = "Falta el cp")
    public Municipio municipio;

    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

}
