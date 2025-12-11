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
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.ML.Rol;

/**
 *
 * @author digis
 */
@Repository
public class RolDAOImplementation implements IRol{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result getAll(){
        Result result = new Result();
        try{
           result.Correct = jdbcTemplate.execute("{CALL getRoles(?)}",(CallableStatementCallback<Boolean>) callStatement -> {
            
            callStatement.registerOutParameter(1, REF_CURSOR);
            callStatement.execute();
            
            ResultSet resultSet = (ResultSet)callStatement.getObject(1);
            
            result.Objects = new ArrayList<>();
            while(resultSet.next()){
                Rol rol = new Rol();
                rol.setIdRol(resultSet.getInt("IDROL"));
                rol.setNombre(resultSet.getString("NOMBRE"));
                result.Objects.add(rol);
            }
            return true;
        });
            
        }catch(Exception ex){
            result.Correct = false;
            result.ex = ex;
            result.ErrorMesagge = ex.getLocalizedMessage();
        }
        return result;
    }
    
}
