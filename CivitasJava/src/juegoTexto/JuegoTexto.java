/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.Dado ;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author miguemumo99
 */
public class JuegoTexto {

    

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<String>  nombres = new ArrayList<>();
        
        int num_jugadores=0;
        System.out.println("Introduce el numero de jugadores(2-4)");

        while (num_jugadores < 2 || num_jugadores > 4){
            num_jugadores = teclado.nextInt();
        }

        System.out.println("Introduce los nombres de los jugadores");
        teclado.nextLine();
        for (int i=0; i<num_jugadores; i++){
            String name = teclado.nextLine();
            System.out.println("Jugador " + name + " introducido");
            nombres.add(name);  
        }
        
        Dado.getInstance().setDebug(true);
        
        CivitasJuego juego = new CivitasJuego(nombres);
        VistaTextual vista = new VistaTextual();
        
        Controlador control = new Controlador(juego, vista);
        control.juega();
    }
    
}
