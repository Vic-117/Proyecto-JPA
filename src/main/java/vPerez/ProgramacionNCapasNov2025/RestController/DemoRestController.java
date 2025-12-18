/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vPerez.ProgramacionNCapasNov2025.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */

@RestController
@RequestMapping("API")
public class DemoRestController {
    
      @GetMapping("suma/{valorUno}+{valorDos}")
    public int Suma(@PathVariable int valorUno, @PathVariable int valorDos){
        int resultado = valorUno + valorDos;        
        return resultado;
    }
   
    @GetMapping("suma1")
    public int SumaRequest(@RequestParam int numeroUno, @RequestParam int numeroDos){
        int resultado = numeroUno + numeroDos;
        return resultado;
    }
    
    @GetMapping("resta/{numeroUno}-{numeroDos}")
    public int Resta(@PathVariable int numeroUno, @PathVariable int numeroDos){
        int resta = numeroUno - numeroDos;       
        return resta;
    }
    @GetMapping("restaDos")
    public int RestaDos(@RequestParam int numeroUno, @RequestParam int numeroDos){
        int resta = numeroUno - numeroDos;       
        return resta;
    }
    
    @GetMapping("multiplicacion/{numeroUno}/{numeroDos}")
    public int MultiplicacionUno(@PathVariable int numeroUno, @PathVariable int numeroDos){
        
        int multiplicacion = numeroUno * numeroDos;       
        return multiplicacion;
    }
    @GetMapping("multiplicacionDos")
    public int MultiplicacionDos(@RequestParam int numeroUno, @RequestParam int numeroDos){
        
        int multiplicacion = numeroUno * numeroDos;
        return multiplicacion;
    }
    
    @GetMapping("division/{numeroUno}/{numeroDos}")
    public int Division(@PathVariable int numeroUno, @PathVariable int numeroDos){
        int division = numeroUno / numeroDos;
        return division;
    }
    
    @GetMapping("divisionDos")
    public int DivisionDos(@RequestParam int numeroUno, @RequestParam int numeroDos){
        int division = numeroUno / numeroDos;
        return division;
    }
    
    @PostMapping("/sumar")
    public int sumar(@RequestBody Calculadora request){
        int resultado = request.getNumero1() + request.getNumero2();
        return resultado;
    }
    
    @PostMapping("/restar")
    public int restar(@RequestBody Calculadora request){
        int resultado = request.getNumero1() - request.getNumero2();
        return resultado;
    }
    
    @PostMapping("/multiplicar")
    public int multiplicar(@RequestBody Calculadora request){
        int resultado = (request.getNumero1()) * (request.getNumero2());
        return resultado;
    }
    
    @PostMapping("/dividir")
    public int dividir(@RequestBody Calculadora request){
        int resultado = request.getNumero1() / request.getNumero2();
        return resultado;
    }
    
}
