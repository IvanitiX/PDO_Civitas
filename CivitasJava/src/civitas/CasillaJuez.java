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
public class CasillaJuez extends Casilla {
    private static int carcel;
    
    public CasillaJuez (int carcel, String nombre) {
        super(nombre) ;
        this.carcel = carcel;
    }
    
    @Override
    public String toString(){
        String s = super.toString() ;
        s+= "\nEl jugador será enviado a la cárcel\n" ;
        return s;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual,todos)){
            super.informe(actual,todos) ;
            todos.get(actual).encarcelar(carcel) ;
        }
    }
    
}
