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
import vPerez.ProgramacionNCapasNov2025.ML.Estado;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class EstadoDAOImplementation implements IEstado {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result getByPais(int idPais) {
        Result result = new Result();

        try {

            result.Correct = jdbcTemplate.execute("{CALL getEstadoByPais(?,?)}", (CallableStatementCallback<Boolean>) callSt -> {

                callSt.setInt(1, idPais);
                callSt.registerOutParameter(2, REF_CURSOR);
                callSt.execute();
                ResultSet resultSet = (ResultSet) callSt.getObject(2);

                result.Objects = new ArrayList<>();

                while (resultSet.next()) {
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IDESTADO"));
                    estado.setNombre(resultSet.getString("NOMBRE"));

                    result.Objects.add(estado);

                }

                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ex = ex;
            result.ErrorMesagge = ex.getLocalizedMessage();
        }
        return result;
    }

}
