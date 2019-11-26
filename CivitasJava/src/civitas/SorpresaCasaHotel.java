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
public class SorpresaCasaHotel extends Sorpresa {
    SorpresaCasaHotel (int valor, String texto ){
        this.init();
        this.valor = valor;
        this.texto = texto;  
    }    
    
    @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos ){
        super.aplicarAJugador(actual, todos);
        Jugador jugador = todos.get(actual);
        jugador.modificarSaldo ( valor * jugador.cantidadCasasHoteles() );
    }
}
