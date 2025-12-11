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
import vPerez.ProgramacionNCapasNov2025.ML.Municipio;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired 
            JdbcTemplate jdbcTemplate;
    
    @Override
    public Result getByEstado(int idEstado) {
        Result result = new Result();
        try{
            
            result.Correct = jdbcTemplate.execute("{CALL getMunicipioByEstado(?,?)}", (CallableStatementCallback<Boolean>) callableSt ->{
                
                callableSt.setInt(1, idEstado);
                callableSt.registerOutParameter(2, REF_CURSOR);
                callableSt.execute();
                
                ResultSet  resulset =(ResultSet) callableSt.getObject(2);
                
                result.Objects = new ArrayList<>();
                while(resulset.next()){
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resulset.getInt("IDMUNICIPIO"));
                    municipio.setNombre(resulset.getString("NOMBRE"));
                    result.Objects.add(municipio);
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
