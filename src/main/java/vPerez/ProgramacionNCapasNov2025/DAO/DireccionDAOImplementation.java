/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import java.sql.ResultSet;
import static java.sql.Types.REF_CURSOR;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vPerez.ProgramacionNCapasNov2025.ML.Colonia;
import vPerez.ProgramacionNCapasNov2025.ML.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.Estado;
import vPerez.ProgramacionNCapasNov2025.ML.Municipio;
import vPerez.ProgramacionNCapasNov2025.ML.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.ML.Usuario;

/**
 *
 * @author digis
 */
@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result getById(int idDirección) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL getDireccionById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idDirección);
                callableStatement.registerOutParameter(2, REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
//                result.Objects = new ArrayList<>();
                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.direcciones = new ArrayList<>();
                    usuario.direcciones.add(new Direccion());
                    usuario.direcciones.get(0).setIdDireccion(resultSet.getInt("IDDIRECCION"));
                    usuario.direcciones.get(0).setCalle(resultSet.getString("CALLE"));
                    usuario.direcciones.get(0).setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                    usuario.direcciones.get(0).setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));

                    usuario.direcciones.get(0).colonia = new Colonia();
                    usuario.direcciones.get(0).getColonia().setIdColonia(resultSet.getInt("IDCOLONIA"));
                    usuario.direcciones.get(0).getColonia().municipio = new Municipio();
                    usuario.direcciones.get(0).getColonia().getMunicipio().setIdMunicipio(resultSet.getInt("IDMUNICIPIO"));
                    usuario.direcciones.get(0).getColonia().getMunicipio().estado = new Estado();
                    usuario.direcciones.get(0).getColonia().getMunicipio().getEstado().setIdEstado(resultSet.getInt("IDESTADO"));
                    usuario.direcciones.get(0).getColonia().getMunicipio().getEstado().pais = new Pais();
                    usuario.direcciones.get(0).getColonia().getMunicipio().getEstado().pais.setIdPais(resultSet.getInt("IDPAIS"));

                    result.Object = usuario;

                }

                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result getDireccionByUsuario(int idUsuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL getDireccionByUsuario(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(idUsuario);
                    usuario.direcciones = new ArrayList<>();

                    usuario.direcciones.add(new Direccion());
                    usuario.direcciones.get(0).setIdDireccion(1);

                    result.Object = usuario;

                    
                    
                }

                return true;
            });
        } catch (Exception ex) {

        }

        return result;

    }

    @Override
    public Result update(Direccion direccion) {
        Result result = new Result();
        try{
            
            result.Correct = jdbcTemplate.execute("{CALL updateDireccion(?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>)callableStatementCallback ->{
             callableStatementCallback.setInt(1, direccion.getIdDireccion());
             callableStatementCallback.setString(2,direccion.getCalle());
             callableStatementCallback.setString(3,direccion.getNumeroInterior());
             callableStatementCallback.setString(4, direccion.getNumeroExterior());
             
             callableStatementCallback.setString(5, direccion.getColonia().getNombre());
             callableStatementCallback.setString(6, direccion.getColonia().municipio.getNombre());
             callableStatementCallback.setString(7, direccion.getColonia().municipio.estado.getNombre());
             callableStatementCallback.setString(8, direccion.getColonia().municipio.getEstado().getNombre());
//             callableStatementCallback.setString(4, direccion.getNumeroExterior());
             int realizado = callableStatementCallback.executeUpdate();
                
                if(realizado ==1){
                    return true;
                    
                }else{
                    return false;
                }
                
             });
           
        }catch(Exception ex){
            result.Correct = false;
            result.ex = ex;
            result.ErrorMesagge = ex.getLocalizedMessage();
        }
        return result;
    }
    
    

}
