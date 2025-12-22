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
import org.springframework.beans.factory.annotation.Value;
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

//    @Autowired
//    private UsuarioMapper usuarioMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> typedQuery = entityManager.createQuery("FROM Usuario ORDER BY idUsuario DESC", Usuario.class);//crear consulta
            List<Usuario> usuarios = typedQuery.getResultList();//obtener resultados de consulta(Usuarios entidades)
            //                                                       FORMA 1

            result.Objects = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                vPerez.ProgramacionNCapasNov2025.ML.Usuario usuarioML = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.ML.Usuario.class);
                result.Objects.add(usuarioML);//Guardando los resultados ya mapeados a DTO
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
    public Result softDelete(Usuario usuario) {
        Result result = new Result();
        try {
            Usuario user = entityManager.find(new Usuario().getClass(), usuario.getIdUsuario());

            if (user != null) {
                user.setEstatus(usuario.getEstatus());

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
    public Result getDireccionUsuarioById(int idUsuario) {
        Result result = new Result();

        try {
            
            Usuario usuarioBusqueda = entityManager.find(new Usuario().getClass(), idUsuario);

            StringBuilder jpql = new StringBuilder();
            if(usuarioBusqueda.direcciones.size() == 0){
                jpql.append("SELECT DISTINCT u FROM Usuario u "
                    + "WHERE u.idUsuario = :idUsuario");
            }else{
                
                jpql.append("SELECT DISTINCT u FROM Usuario u "
                        + "JOIN FETCH u.direcciones d "
                        + "JOIN FETCH d.colonia col "
                        + "JOIN FETCH col.municipio mun "
                        + "JOIN FETCH mun.estado est "
                        + "JOIN FETCH est.pais p "
                        + "WHERE u.idUsuario = :idUsuario");//:idUsuario es el parametro pasado
            }
            


            Usuario usuario = entityManager.createQuery(jpql.toString(), Usuario.class) //Usando el jpql sobre la entidad usuarioJPA
                    .setParameter("idUsuario", idUsuario)//Pasando parametros a la query
                    .getSingleResult();
//            ModelMapper modelMapperr = new ModelMapper();
            //Transformando JPAEntity a DTO(Que necesita la vista)
            vPerez.ProgramacionNCapasNov2025.ML.Usuario usuarioML = modelMapper.map(usuario, vPerez.ProgramacionNCapasNov2025.ML.Usuario.class);
//    result.Objects = new ArrayList<>();
            result.Object = usuarioML;
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

//    @Value("${hibernate.jdbc.batch_size:50}") // Usa el valor configurado, por defecto 50
    private int batchSize;

    
    @Transactional
    @Override
    public Result addMany(List<Usuario> usuarios) {
        Result result = new Result();
        try {

            int i = 0;
            result.Objects = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                entityManager.persist(usuario);

                usuario.direcciones.get(0).Usuario = new Usuario();
                usuario.direcciones.get(0).Usuario.setIdUsuario(usuario.getIdUsuario());

                entityManager.persist(usuario.direcciones.get(0));

                //AÑADIDO RECIEN 16/12/2025
                if (i % batchSize == 0 && i > 0) {
                    entityManager.flush();
                    entityManager.clear();
                }

            }
            //AÑADIDO RECIEN 16/12/2025
            entityManager.flush();

            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    
    
    //BUSQUEDA DINAMICA
    @Override
    public Result GetAllDinamico(Usuario usuario) {
        Result result = new Result();

        //String builder trabaja sobre la misma cadena, un String normal si se modifica se crea otro con la modificacion en otro espacio de memoria
        StringBuilder query = new StringBuilder("FROM Usuario WHERE UPPER(nombre) LIKE UPPER(:nombre) AND UPPER(apellidoPaterno) LIKE UPPER(:apellidoPaterno) AND UPPER(apellidoMaterno) LIKE UPPER(:apellidoMaterno)");

        //si tiene rol la busqueda
        if (usuario.rol.getIdRol() != 0) {
            query.append(" AND rol.idRol = :idRol");
        }

        TypedQuery<Usuario> queryUsuarios = entityManager.createQuery(query.toString(), Usuario.class);

        queryUsuarios.setParameter("nombre", "%" + usuario.getNombre() + "%");//asignar parametros de entrada
        queryUsuarios.setParameter("apellidoPaterno", "%" + usuario.getApellidoPaterno()+ "%");
        queryUsuarios.setParameter("apellidoMaterno", "%" + usuario.getApellidoMaterno()+ "%");

        if (usuario.rol.getIdRol() != 0) {
            queryUsuarios.setParameter("idRol", usuario.rol.getIdRol());
        }

        List<Usuario> usuarios = queryUsuarios.getResultList();
        result.Objects = new ArrayList<>();

        for (Usuario item : usuarios) {
            result.Objects.add(item);
        }
        return result;
    }

}
