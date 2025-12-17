/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import java.util.List;
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
    
    public Result update(Usuario usuario);
    
    public Result delete(int idUsuario);
    
    public Result softDelete(Usuario usuario);
    
    public Result getDireccionUsuarioById(int idUsuario);
    
    public Result addMany(List<Usuario> usuarios);
}
