/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import vPerez.ProgramacionNCapasNov2025.JPA.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
public interface IDireccionJPA {
    
    public Result add(Direccion direccion,int idUsuario);
    
    public Result update(Direccion direccion);
    
    public Result delete(int idDireccion);
    
    
    
    
}
