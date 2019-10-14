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
public class TestP2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       ArrayList<String> jugadores = new ArrayList<>() ;
       jugadores.add("Iván");
       jugadores.add("Mumo");
       
       CivitasJuego juego = new CivitasJuego(jugadores) ;
       
       juego.actualizarInfo();
       
       for (int i = 0 ; i < 5 ; i++){
           System.out.println(juego.getMazo().siguiente().toString()) ;
       }
       
       System.out.println(juego.getTablero().getCasilla(0).toString());
       System.out.println(juego.getTablero().getCasilla(15).toString());
       System.out.println(juego.getTablero().getCasilla(4).toString());
       System.out.println(juego.getTablero().getCasilla(17).toString());
       
       juego.actualizarInfo();
       //juego.pasarTurno() ;
       juego.actualizarInfo();
       
       System.out.println(juego.ranking()) ;
       
       juego.getJugadorActual().modificarSaldo(-10000) ;
       juego.actualizarInfo();
    }
    
}
