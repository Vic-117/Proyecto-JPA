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
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class ColoniaDAOImplementation implements IColonia {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Result getColoniaByMunicipio(int idMunicipio) {
        Result result = new Result();
        try{
            result.Correct = jdbcTemplate.execute("{CALL getColoniaByMunicipio(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, idMunicipio);
                callableStatement.registerOutParameter(2, REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.Objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IDCOLONIA"));
                    colonia.setNombre(resultSet.getString("NOMBRE"));
                    colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                    result.Objects.add(colonia);
                }
                
                
                return true;
            });
            
            
        }catch(Exception ex){
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
        
    }
    
    
}
