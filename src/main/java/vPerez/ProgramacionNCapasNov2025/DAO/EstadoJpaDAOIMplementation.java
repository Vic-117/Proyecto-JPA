/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vPerez.ProgramacionNCapasNov2025.JPA.Estado;
import vPerez.ProgramacionNCapasNov2025.JPA.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class EstadoJpaDAOIMplementation implements IEstadoJPA {
    
    
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    EntityManager entityManager;

    @Override
    public Result getByPais(int idPais) {
        Result result = new Result();
        try{
            String jpql = "SELECT e FROM Estado e"
                    + " JOIN FETCH e.pais p "
                    + "WHERE p.idPais = :idPais";
            TypedQuery<Estado> typedQuery = entityManager.createQuery(jpql,Estado.class).setParameter("idPais", idPais);
            List<Estado> estados = typedQuery.getResultList();//Obteniendo el resultado de la consulta
            
            result.Objects = new ArrayList<>();
            
            for(Estado estado:estados){
                vPerez.ProgramacionNCapasNov2025.ML.Estado estadoML= modelMapper.map(estado, vPerez.ProgramacionNCapasNov2025.ML.Estado.class);
                result.Objects.add(estadoML);
            }
            
            result.Correct = true;
            

            
        }catch(Exception ex){
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
        
    }
    
}
