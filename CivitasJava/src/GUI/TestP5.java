/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author ivan
 */
public class TestP5 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CivitasView vista = new CivitasView() ;
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(true);
        CapturaNombres nombres = new CapturaNombres(vista,true) ;
        ArrayList<String> lista_nombres = nombres.getNombres() ;
        CivitasJuego juego = new CivitasJuego(lista_nombres) ;
        Controlador controlador = new Controlador(juego,vista) ;
        vista.setCivitasJuego(juego);
        vista.actualizarVista();
        controlador.juega();
    }
    
}
