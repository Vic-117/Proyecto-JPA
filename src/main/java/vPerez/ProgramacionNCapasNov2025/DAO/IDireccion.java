/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import vPerez.ProgramacionNCapasNov2025.ML.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
public interface IDireccion {
   
    public Result getDireccionByUsuario(int idUsuario);
    public Result getById(int idDirección);
    public Result update(Direccion direccion);
    
}
