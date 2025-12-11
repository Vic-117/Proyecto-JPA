/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import vPerez.ProgramacionNCapasNov2025.JPA.Direccion;
import vPerez.ProgramacionNCapasNov2025.JPA.Usuario;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
public interface IUsuarioJPA {
    public Result getAll();
    
    public Result add(Usuario usuario);
}
