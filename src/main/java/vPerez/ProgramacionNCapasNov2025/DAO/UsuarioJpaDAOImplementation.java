/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vPerez.ProgramacionNCapasNov2025.JPA.Direccion;
import vPerez.ProgramacionNCapasNov2025.JPA.Usuario;
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.Mapper.UsuarioMapper;

/**
 *
 * @author digis
 */
@Repository
public class UsuarioJpaDAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> typedQuery = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = typedQuery.getResultList();//Usuarios entidades
            //                                                       FORMA 1

            result.Objects = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                vPerez.ProgramacionNCapasNov2025.ML.Usuario usuarioML = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.ML.Usuario.class);
                result.Objects.add(usuarioML);
            }

            //                                                     FORMA 2.
            //Conversion que se hace automaticamente por el mapper:
//          List< vPerez.ProgramacionNCapasNov2025.ML.Usuario> dtos = usuarioMapper.toDTOList(usuarios);
//          result.Objects = new ArrayList<>();
//          result.Objects.addAll(dtos);
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
    public Result add(Usuario usuario) {
        Result result = new Result();
        try {

            entityManager.persist(usuario);//Para que genere el id

            usuario.direcciones.get(0).Usuario = new Usuario();
            usuario.direcciones.get(0).Usuario.setIdUsuario(usuario.getIdUsuario());
//            Despues de que genere el id realizamos el proceso de la direccion
//            usuario.direcciones.get(0).usuario = new Usuario();
//            usuario.direcciones.get(0).usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(usuario.direcciones.get(0));

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result update(Usuario usuario) {
        Result result = new Result();
        try {

            Usuario user = entityManager.find(usuario.getClass(), usuario.getIdUsuario());//Obtiene el usuario con ese id de la bd
            if (user != null) {

                usuario.direcciones.clear();
                for (Direccion direccion : user.direcciones) {
                    usuario.direcciones.add(direccion);

                }

                result.Object = usuario;
                entityManager.merge(usuario);//Actualiza incluido a direcciones, las borra por que se envian vacias
                result.Correct = true;
            } else {
                result.Correct = false;
//                throw new Exception("Error al actualizar");
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result delete(int idUsuario) {
        Result result = new Result();
        try {
            Usuario user = entityManager.find(new Usuario().getClass(), idUsuario);
            if (user != null) {
                entityManager.remove(user);
                result.Correct = true;
            } else {
                result.Correct = false;
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Transactional
    @Override
    public Result softDelete(Usuario usuario ) {
        Result result = new Result();
        try {
            Usuario user = entityManager.find(new Usuario().getClass(), usuario.getIdUsuario());

            if (user != null) {
                user.setEstatus(usuario.getEstatus());
                
                result.Correct = true;
            }else{
                result.Correct = false;
                
                
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result getDireccionUsuarioById(int idUsuario) {
        Result result = new Result();
       
        try{
            
            String jpql = "SELECT DISTINCT u FROM Usuario u " +
                  "JOIN FETCH u.direcciones d " +
                  "JOIN FETCH d.colonia col " +
                  "JOIN FETCH col.municipio mun " +
                  "JOIN FETCH mun.estado est " +
                  "JOIN FETCH est.pais p " +
                  "WHERE u.idUsuario = :idUsuario";
            
            Usuario usuario = entityManager.createQuery(jpql, Usuario.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
//            ModelMapper modelMapperr = new ModelMapper();
           vPerez.ProgramacionNCapasNov2025.ML.Usuario  usuarioML = usuarioMapper.toDTO(usuario);
//    result.Objects = new ArrayList<>();
            result.Object = usuarioML;
            result.Correct = true;
        }catch(Exception ex){
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
     
    

        return result;
   }

}
