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
public class CasillaSorpresa extends Casilla {
     private Sorpresa sorpresa ;
     private MazoSorpresas mazo ;
     
     public CasillaSorpresa (MazoSorpresas mazo, String nombre) {
        super(nombre) ;
        this.mazo = mazo;
        this.sorpresa = mazo.siguiente() ;
    }
     
     @Override
    public String toString(){
        String s = super.toString() ;
        s+= "\nLa sorpresa que saldrá es:\n" + sorpresa.toString() ;
        return s;
    }
     
     @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual,todos)){
            super.informe(actual, todos);
           sorpresa = mazo.siguiente() ;
           this.informe(actual, todos);
           sorpresa.aplicarAJugador(actual, todos);
        }
    }
}
