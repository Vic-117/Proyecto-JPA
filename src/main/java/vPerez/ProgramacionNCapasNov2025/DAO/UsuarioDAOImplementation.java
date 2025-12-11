/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.DAO;

import java.sql.ResultSet;
import static java.sql.Types.REF_CURSOR;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vPerez.ProgramacionNCapasNov2025.ML.Result;
import vPerez.ProgramacionNCapasNov2025.DAO.IUsuario;
import vPerez.ProgramacionNCapasNov2025.ML.Colonia;
import vPerez.ProgramacionNCapasNov2025.ML.Direccion;
import vPerez.ProgramacionNCapasNov2025.ML.Estado;
import vPerez.ProgramacionNCapasNov2025.ML.Municipio;
import vPerez.ProgramacionNCapasNov2025.ML.Pais;
import vPerez.ProgramacionNCapasNov2025.ML.Rol;
import vPerez.ProgramacionNCapasNov2025.ML.Usuario;

/**
 *
 * @author digis
 */
//Clase wque maneja acciones con base de datos
@Repository
public class UsuarioDAOImplementation implements IUsuario {

    //Inyeccion automatica de dependencias
    @Autowired
    private JdbcTemplate jdbcTemplate;//Permite conexión a db

    //Sobreescritura del metod de la interfaz(Realizando una implementación)
    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            //Ejecucion de consulta
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDireccionGetAll(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                //Agregando parametro de salida(ese parametro lo usa el cursor)
                callableStatement.registerOutParameter(1, REF_CURSOR);
                //Ejecutando Stored procedure
                callableStatement.execute();

                //Almacenando el resultado de la consulta en un ResultSet
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                result.Objects = new ArrayList<>();
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IDUSUARIO");

                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == idUsuario) {
                        Direccion direccion = new Direccion();
                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.setIdDireccion(resultSet.getInt("IDDIRECCION"));
                        direccion.setCalle(resultSet.getString("CALLE"));
                        direccion.setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                        direccion.setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));
                        direccion.colonia.setNombre(resultSet.getString("NOMBRECOLONIA"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("MUNICIPIONOMBRE"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("ESTADONOMBRE"));
                        direccion.colonia.municipio.estado.pais.setNombre(resultSet.getString("PAISNOMBRE"));
                        Usuario usuarioExistente = (Usuario) result.Objects.get(result.Objects.size() - 1);
                        usuarioExistente.direcciones.add(direccion);
                    } else {
                        Usuario user = new Usuario();
                        Direccion dir = new Direccion();

                        user.direcciones = new ArrayList<>();
                        user.rol = new Rol();
                        dir.colonia = new Colonia();
                        dir.colonia.municipio = new Municipio();
                        dir.colonia.municipio.estado = new Estado();
                        dir.colonia.municipio.estado.pais = new Pais();

                        user.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                        user.setNombre(resultSet.getString("NOMBREUSUARIO"));
                        user.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                        user.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                        user.setEmail(resultSet.getString("EMAIL"));
                        user.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                        user.setSexo(resultSet.getString("SEXO"));
                        user.setTelefono(resultSet.getString("TELEFONO"));
                        user.setCelular(resultSet.getString("CELULAR"));
                        user.setCurp(resultSet.getString("CURP"));
                        user.rol.setIdRol(resultSet.getInt("IDROL"));

                        int idDireccion = resultSet.getInt("IDDIRECCION");
                        if (idDireccion != 0) {

                            dir.setIdDireccion(resultSet.getInt("IDDIRECCION"));
                            dir.setCalle(resultSet.getString("CALLE"));
                            dir.setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                            dir.setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));
                            dir.colonia.setNombre(resultSet.getString("NOMBRECOLONIA"));
                            dir.colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                            dir.colonia.municipio.setNombre(resultSet.getString("MUNICIPIONOMBRE"));
                            dir.colonia.municipio.estado.setNombre(resultSet.getString("ESTADONOMBRE"));
                            dir.colonia.municipio.estado.pais.setNombre(resultSet.getString("PAISNOMBRE"));
                            user.direcciones.add(dir);
                        }
                        result.Objects.add(user);
                    }

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

    @Override
    public Result Add(Usuario user) {
        Result result = new Result();

        result.Correct = jdbcTemplate.execute("{CALL AlumnoDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            callableStatement.setString(1, user.getNombre());
            callableStatement.setString(2, user.getApellidoPaterno());
            callableStatement.setString(3, user.getApellidoMaterno());
            callableStatement.setString(4, user.getEmail());
            callableStatement.setString(5, user.getPassword());

            callableStatement.setDate(6, new java.sql.Date(user.getFechaNacimiento().getTime()));
            callableStatement.setInt(7, user.getRol().getIdRol());
            callableStatement.setString(8, user.getSexo());
            callableStatement.setString(9, user.getTelefono());
            callableStatement.setString(10, user.getCelular());
            callableStatement.setString(11, user.getCurp());
            callableStatement.setString(12, user.direcciones.get(0).getCalle());
            callableStatement.setString(13, user.direcciones.get(0).getNumeroInterior());
            callableStatement.setString(14, user.direcciones.get(0).getNumeroExterior());
            callableStatement.setInt(15, user.direcciones.get(0).getColonia().getIdColonia());

            callableStatement.execute();

            return true;
        });

        try {

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetDireccionUsuarioById(int id) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDureccionGetByID(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setInt(1, id);
                callableStatement.registerOutParameter(2, REF_CURSOR);
                callableStatement.execute();
                ResultSet reST = (ResultSet) callableStatement.getObject(2);

                result.Objects = new ArrayList<>();
                //Usuario user = (Usuario)result.Object;

                //Si se posiciona dentro del objeto en la siguiente fila hace:
                if (reST.next()) {

                    Usuario user = new Usuario();
                    user.rol = new Rol();
                    user.direcciones = new ArrayList<>();

                    user.setIdUsuario(reST.getInt("IDUSUARIO"));
                    user.setNombre(reST.getString("USERNAME"));
                    user.setApellidoMaterno(reST.getString("APELLIDOMATERNO"));
                    user.setApellidoPaterno(reST.getString("APELLIDOPATERNO"));
                    user.setEmail(reST.getString("EMAIL"));
                    user.setPassword(reST.getString("PASSWORD"));
                    user.setFechaNacimiento(reST.getDate("FECHANACIMIENTO"));
                    user.rol.setIdRol(reST.getInt("IDROL"));
                    user.setSexo(reST.getString("SEXO"));
                    user.setTelefono(reST.getString("TELEFONO"));
                    user.setCelular(reST.getString("CELULAR"));
                    user.setCurp(reST.getString("CURP"));

                    //HAz esto 
                    do {
                        Direccion direccion = new Direccion();
                        direccion.colonia = new Colonia();
                        direccion.setIdDireccion(reST.getInt("IDDIRECCION"));
                        direccion.setCalle(reST.getString("CALLE"));
                        direccion.setNumeroInterior(reST.getString("NUMEROINTERIOR"));
                        direccion.setNumeroExterior(reST.getString("NUMEROEXTERIOR"));
                        direccion.colonia.setIdColonia(reST.getInt("IDCOLONIA"));
                        user.direcciones.add(direccion);
                        //Mientras que haya una siguiente fila
                    } while (reST.next());

                    result.Object = user;
                }
                return true;
            }
            );

        } catch (Exception ex) {
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
            result.Correct = false;

        }
        return result;
    }

    @Override
    public Result Delete(int id) {
        Result result = new Result();
        try {
            result.Correct = jdbcTemplate.execute("{CALL UsuarioDireccionDelete(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.setInt(1, id);
                callableStatement.execute();

                return true;
            });

        } catch (Exception ex) {

            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;

        }
        return result;
    }

    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {

            result.Correct = jdbcTemplate.execute("{CALL UsuarioGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                    usuario.setNombre(resultSet.getString("USERNAME"));
                    usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                    usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                    usuario.setEmail(resultSet.getString("EMAIL"));
                    usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                    usuario.rol = new Rol();
                    usuario.rol.setIdRol(resultSet.getInt("IDROL"));
                    usuario.rol.setNombre(resultSet.getString("NOMBREROL"));
                    usuario.setSexo(resultSet.getString("SEXO"));
                    usuario.setTelefono(resultSet.getString("TELEFONO"));
                    usuario.setCelular(resultSet.getString("CELULAR"));
                    usuario.setCurp(resultSet.getString("CURP"));
                    result.Object = usuario;

                }

                return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();
        try {

            result.Correct = jdbcTemplate.execute("{CALL updateUsuario(?,?,?,?,?,?,?,?,?,?,?,?) }", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setString(6, usuario.getPassword());
                callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));//Posible falla
                callableStatement.setInt(8, usuario.getRol().getIdRol());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.getCurp());

                int rowAffecteds = callableStatement.executeUpdate();
                if (rowAffecteds > 0) {
                    return true;

                } else {
                    return false;
                }
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result AddMany(List<Usuario> usuarios) {

        Result result = new Result();

        try {

            jdbcTemplate.batchUpdate("{CALL AlumnoDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", usuarios, usuarios.size(), (CallableStatement, usuario) -> {

                CallableStatement.setString(1, usuario.getNombre());
                CallableStatement.setString(2, usuario.getApellidoPaterno());
                CallableStatement.setString(3, usuario.getApellidoMaterno());
                CallableStatement.setString(4, usuario.getEmail());
                CallableStatement.setString(5, usuario.getPassword());
                CallableStatement.setDate(6, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                CallableStatement.setInt(7, usuario.getRol().getIdRol());
                CallableStatement.setString(8, usuario.getSexo());
                CallableStatement.setString(9, usuario.getTelefono());
                CallableStatement.setString(10, usuario.getCelular());
                CallableStatement.setString(11, usuario.getCurp());
                CallableStatement.setString(12, usuario.getDirecciones().get(0).getCalle());
                CallableStatement.setString(13, usuario.getDirecciones().get(0).getNumeroInterior());
                CallableStatement.setString(14, usuario.getDirecciones().get(0).getNumeroExterior());
                CallableStatement.setInt(15, usuario.direcciones.get(0).getColonia().getIdColonia());
//                    result.Objects = new ArrayList<>();
//                    result.Objects.add(usuario);

//                    return true;
            });

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result search(Usuario usuario) {
        Result result = new Result();

        try {
            result.Correct = jdbcTemplate.execute("{CALL busquedaUsuarios(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.getRol().getIdRol());
                callableStatement.registerOutParameter(5, REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.Objects = new ArrayList<>();
                
                while (resultSet.next()) {
                    int idUsuario = resultSet.getInt("IDUSUARIO");

                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result.Objects.size() - 1)).getIdUsuario() == idUsuario) {
                        Direccion direccion = new Direccion();
                        direccion.colonia = new Colonia();
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.pais = new Pais();

                        direccion.setIdDireccion(resultSet.getInt("IDDIRECCION"));
                        direccion.setCalle(resultSet.getString("CALLE"));
                        direccion.setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                        direccion.setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));
                        direccion.colonia.setNombre(resultSet.getString("NOMBRECOLONIA"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                        direccion.colonia.municipio.setNombre(resultSet.getString("MUNICIPIONOMBRE"));
                        direccion.colonia.municipio.estado.setNombre(resultSet.getString("ESTADONOMBRE"));
                        direccion.colonia.municipio.estado.pais.setNombre(resultSet.getString("PAISNOMBRE"));
                        Usuario usuarioExistente = (Usuario) result.Objects.get(result.Objects.size() - 1);
                        usuarioExistente.direcciones.add(direccion);
                    } else {
                        Usuario user = new Usuario();
                        Direccion dir = new Direccion();

                        user.direcciones = new ArrayList<>();
                        user.rol = new Rol();
                        dir.colonia = new Colonia();
                        dir.colonia.municipio = new Municipio();
                        dir.colonia.municipio.estado = new Estado();
                        dir.colonia.municipio.estado.pais = new Pais();

                        user.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                        user.setNombre(resultSet.getString("NOMBREUSUARIO"));
                        user.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                        user.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                        user.setEmail(resultSet.getString("EMAIL"));
                        user.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                        user.setSexo(resultSet.getString("SEXO"));
                        user.setTelefono(resultSet.getString("TELEFONO"));
                        user.setCelular(resultSet.getString("CELULAR"));
                        user.setCurp(resultSet.getString("CURP"));
                        user.rol.setIdRol(resultSet.getInt("IDROL"));
                        user.rol.setNombre(resultSet.getString("NOMBREROL"));

                        int idDireccion = resultSet.getInt("IDDIRECCION");
                        if (idDireccion != 0) {

                            dir.setIdDireccion(resultSet.getInt("IDDIRECCION"));
                            dir.setCalle(resultSet.getString("CALLE"));
                            dir.setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                            dir.setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));
                            dir.colonia.setNombre(resultSet.getString("NOMBRECOLONIA"));
                            dir.colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                            dir.colonia.municipio.setNombre(resultSet.getString("MUNICIPIONOMBRE"));
                            dir.colonia.municipio.estado.setNombre(resultSet.getString("ESTADONOMBRE"));
                            dir.colonia.municipio.estado.pais.setNombre(resultSet.getString("PAISNOMBRE"));
                            user.direcciones.add(dir);
                        }
                        result.Objects.add(user);
                    }

                }
                return true;

            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMesagge = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
