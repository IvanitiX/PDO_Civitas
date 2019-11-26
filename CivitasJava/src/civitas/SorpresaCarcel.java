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
public class SorpresaCarcel extends Sorpresa{
    SorpresaCarcel (Tablero tablero){
        this.init();
        this.tablero = tablero;
        this.texto = "Te han pillado dando galletas Oreo rellenas de pasta de dientes.";      
    }
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos ){ 
        super.aplicarAJugador(actual, todos);
        todos.get(actual).encarcelar( tablero.getCarcel() );  
    }
}
