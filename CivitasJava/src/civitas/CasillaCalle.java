/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class CasillaCalle extends Casilla {
    private float importe ;
    private TituloPropiedad titulo ;
    
    public CasillaCalle (TituloPropiedad titulo) {
        super(titulo.getNombre()) ;
        this.titulo = titulo;
        this.importe = titulo.getPrecioCompra() ;
    }
    
    public TituloPropiedad getTituloPropiedad(){
         return titulo;
    }
    
    public String toString(){
        String s = super.toString() ;
        s += "\n" + titulo.toString() ;
        return s ;
    }
    
    @Override
    void recibeJugador (int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual,todos)){
            super.informe(actual,todos) ;
           Jugador jugador = todos.get(actual) ;
           if(!titulo.tienePropietario()){
               jugador.puedeComprarCasilla() ;
           }
           else{
               titulo.tramitarAlquiler(jugador);
           }
        }
    }
    
    
}
