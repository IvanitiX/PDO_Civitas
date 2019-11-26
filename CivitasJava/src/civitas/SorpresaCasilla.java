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
public class SorpresaCasilla extends Sorpresa {
     SorpresaCasilla (Tablero tablero, int valor, String texto ){
        this.init();
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;       
    }
     
     @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos ){     
        informe(actual, todos);
        int numCasilla = todos.get(actual).getNumCasillaActual();
        int numTirada = tablero.calcularTirada(numCasilla, valor);
        int nuevaPosicion = tablero.nuevaPosicion(numCasilla, numTirada);
        todos.get(actual).moverACasilla(nuevaPosicion);
        numCasilla = todos.get(actual).getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasilla);
        //casilla.recibeJugador(actual,todos);
    }

}
