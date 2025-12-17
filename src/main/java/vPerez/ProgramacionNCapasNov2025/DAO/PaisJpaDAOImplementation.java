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
import vPerez.ProgramacionNCapasNov2025.JPA.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class PaisJpaDAOImplementation implements IPaisJPA{

    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    EntityManager entityManager;
    @Override
    public Result getAll() {
        Result result = new Result();
        try{
            
            TypedQuery<Pais> typedQuery = entityManager.createQuery("FROM Pais", Pais.class);
            List<Pais> paises = typedQuery.getResultList();
            
            result.Objects = new ArrayList<>();
            
            for(Pais pais: paises){
                vPerez.ProgramacionNCapasNov2025.ML.Pais paisML = modelMapper.map(pais,vPerez.ProgramacionNCapasNov2025.ML.Pais.class);
                result.Objects.add(paisML);
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
