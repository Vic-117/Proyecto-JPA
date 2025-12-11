/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.Configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author digis
 */
@Configuration
public class ModelMapperConfig {
  //Configuracion para que el mapeo sea correcto entre las entidades JPA y las clases DTO
    @Bean
 public ModelMapper modelMapper(){
     ModelMapper modelMapper = new ModelMapper();
     
     modelMapper.getConfiguration().
             setMatchingStrategy(MatchingStrategies.STRICT)
             .setAmbiguityIgnored(true)
             .setSkipNullEnabled(true)
             .setFieldMatchingEnabled(true)
             .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
     
     return modelMapper;
 }
    
}
