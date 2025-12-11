/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.Configuration;

import java.sql.DriverManager;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author digis
 */
@Configuration
public class DataSourceConfiguration {

    @Bean//Para terminar de configurar//    Conexión a la base de datos
    public DataSource dataSource() {
        
        
        //Me permitirá conectar a java con bd
        DriverManagerDataSource  dataSource = new DriverManagerDataSource();
        //Url de la bd para la conexión
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        //nombre de usuario para acceder a la db
        dataSource.setUsername(" VPerezProgramacionNCapasNoviembre25");
//        dataSource.setUsername(" ProgramacionVic");
//        Contraseña de usuario para acceder a la bd
        dataSource.setPassword("password1");
//        dataSource.setPassword("5522");
        
        return dataSource; 
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
