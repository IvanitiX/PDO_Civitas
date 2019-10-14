/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Miguel Muñoz Molina
 * @author Iván Valero Rodríguez
 */

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas ;
    private boolean barajada;
    private int usadas ;
    private boolean debug ;
    private ArrayList<Sorpresa> cartasEspeciales ;
    private Sorpresa ultimaSorpresa ;
    private Diario diario = Diario.getInstance() ;
    
    private void init(){
        sorpresas = new ArrayList<Sorpresa>() ;
        cartasEspeciales = new ArrayList<Sorpresa>() ;
        barajada = false ;
        usadas = 0 ;
    }
    
    MazoSorpresas (boolean modo){
        init();
        debug = modo ;
        if (debug){
            diario.ocurreEvento("Modo debug para el mazo: Activado");
        }
    }
        
    MazoSorpresas (){
        init();
        debug = false ;
    }
    
    void alMazo(Sorpresa s){
        if (!barajada)
            sorpresas.add(s);
    }
    
    Sorpresa siguiente(){
        if (!barajada || usadas == sorpresas.size()){
            Collections.shuffle(sorpresas);
            usadas = 0;
            barajada = true ;
        }
        usadas++ ;
        ultimaSorpresa = sorpresas.get(0) ;
        sorpresas.remove(0) ;
        sorpresas.add(ultimaSorpresa) ;
        return ultimaSorpresa ;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            sorpresas.remove(sorpresa) ;
            cartasEspeciales.add(sorpresa) ;
            diario.ocurreEvento("Se ha inhabilitado la sorpresa :" + sorpresa.toString());
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.contains(sorpresa)){
            cartasEspeciales.remove(sorpresa) ;
            sorpresas.add(sorpresa) ;
            diario.ocurreEvento("Se ha habilitado la sorpresa :" + sorpresa.toString());
        }
    }
    
}
