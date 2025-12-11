/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import java.util.List;
import org.springframework.stereotype.Repository;
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.ML.Usuario;

/**
 *
 * @author digis
 */

public interface IUsuario {
    public Result GetAll();
    
    public Result Add(Usuario user);
    
    public Result GetDireccionUsuarioById(int id);
    
    public Result Delete(int id);
    
    public Result GetById(int idUsuario);
    
    public Result UpdateUsuario(Usuario usuario);
    
    public Result AddMany(List<Usuario> usuarios);
    
    public Result search(Usuario usuario);
    
    
    
    
}
