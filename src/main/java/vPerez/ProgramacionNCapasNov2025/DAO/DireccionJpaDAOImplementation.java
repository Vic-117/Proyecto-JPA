/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vPerez.ProgramacionNCapasNov2025.JPA.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.Result;

/**
 *
 * @author digis
 */
@Repository
public class DireccionJpaDAOImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result update(Direccion direccion) {
        Result result = new Result();
        try {
//            Direccion dir = entityManager.find(direccion.getClass(), direccion.getIdDireccion());
//            if(dir !=null){
            entityManager.merge(direccion);
//            }else{
//                result.Correct = false;
//            }

            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result delete(int idDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = entityManager.find(new Direccion().getClass(), idDireccion);

            if (direccion != null) {
                entityManager.remove(direccion);
                result.Object = "Operacion realizada con exito";
                result.Correct = true;
                
            } else {
                result.Object = "La operación fracasó con exito xd";
                result.Correct = false;
            }
            

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
