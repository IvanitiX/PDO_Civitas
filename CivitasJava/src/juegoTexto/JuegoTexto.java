/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado ;
import java.util.ArrayList;

/**
 *
 * @author miguemumo99
 */
public class JuegoTexto {

    

    public static void main(String[] args) {
        ArrayList<String>  nombres = new ArrayList<>();
        nombres.add("Ivan");
        nombres.add("Mumo");
        
        Dado.getInstance().setDebug(true);
        
        CivitasJuego juego = new CivitasJuego(nombres);
        VistaTextual vista = new VistaTextual();
        
        Controlador control = new Controlador(juego, vista);
        control.juega();
    }
    
}
