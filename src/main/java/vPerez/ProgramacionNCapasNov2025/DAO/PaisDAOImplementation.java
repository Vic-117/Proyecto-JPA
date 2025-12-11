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
import vPerez.ProgramacionNCapasNov2025.ML.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class PaisDAOImplementation implements IPais{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result getAll() {
        Result result = new Result();
        
        try{
            result.Correct = jdbcTemplate.execute("{CALL getPais(?)}", (CallableStatementCallback<Boolean>)callState -> {
            callState.registerOutParameter(1, REF_CURSOR);
            callState.execute();
            
            ResultSet reST = (ResultSet)callState.getObject(1);
            result.Objects = new ArrayList<>();
            while(reST.next()){
                Pais pais = new Pais();
                pais.setIdPais(reST.getInt("IDPAIS"));
                pais.setNombre(reST.getString("NOMBRE"));
                result.Objects.add(pais);
            }
            return true;
        });
        }catch(Exception ex){
            result.Correct= false;
            result.ex = ex;
            result.ErrorMesagge = ex.getLocalizedMessage();
        }
        return result;
    }
    
}
