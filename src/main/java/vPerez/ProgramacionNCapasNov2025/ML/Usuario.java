/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.ML;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author digis
 */
public class Usuario {

    private int idUsuario;
    @NotNull(message = "Escribe algo...")
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    @Size(min = 2, max = 49)
    private String nombre;
    @NotNull(message = "Escribe algo...")
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    @Size(min = 2, max = 79)
    private String apellidoPaterno;
    @NotNull(message = "Escribe algo...")
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    @Size(min = 2, max = 49)
    private String apellidoMaterno;
    @NotNull(message = "Escribe algo...")
    @Size(min = 13, max = 253)
    @Pattern(regexp = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$", message = "Escribe un correo valido")
    private String email;
    @NotNull(message = "Escribe algo...")
    @Size(min = 8, max = 50)
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    private String password;
    @NotNull(message = "Registra tu fecha de nacimiento.")
//    @NotEmpty(message = "Campo requerido, por favor llenalo")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @NotNull(message = "Seleccione un elemento.")
//    @NotEmpty(message = "Campo requerido, por favor llenalo")
    public Rol rol;
    @Size(min = 1, max = 1)
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    private String sexo;
    @NotNull(message = "Escribe algo...")
    @Positive
    @Size(min = 10, max = 49)
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    private String telefono;
    @NotNull(message = "Escribe algo...")
    @Positive
    @Size(min = 10, max = 49)
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    private String celular;
    @NotNull(message = "Escribe algo...")
    @Pattern(regexp = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1}", message = "CURP no valida")
    @Size(min = 10, max = 49)
    @NotEmpty(message = "Campo requerido, por favor llenalo")
    private String curp;
//    private List<Direccion> direcciones = new ArrayList<>();//Relacion del lado de 1, un usuario tiene muchas direcciones
    @NotNull(message = "Ingresa una direccion")
    public List<Direccion> direcciones;//Relacion del lado de 1, un usuario tiene muchas direcciones

    public Usuario() {

    }

    public Usuario(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String password, Date fechaNacimiento) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getSexo() {
        return sexo;

    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

}
