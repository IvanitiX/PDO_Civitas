/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 * @author Miguel Muñoz Molina
 * @author Iván Valero Rodríguez
 */
public class Casilla {
    private String nombre ;
    private float importe ;
    private static int carcel;
    private TituloPropiedad titulo ;
    private Sorpresa sorpresa ;
    private MazoSorpresas mazo ;
    private Diario diario = Diario.getInstance() ;

    Casilla (String nombre){
        this.nombre = nombre ;
    }
    

    
    void informe (int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos))
            diario.ocurreEvento(todos.get(actual).getNombre()+ " ha llegado a la casilla:\n" + toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return ( actual >= 0 && actual < todos.size() );
    }
    
    @Override
    public String toString(){
        String clase = this.getClass().getSimpleName() ;
        String s = "------" + clase + "------\n"  + "Nombre: " + nombre ;
        if (clase == "Casilla"){
            s+= "\nEstas descansando...";
        }
        return s ;
    }
    
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
    }
    
    
    public String getNombre() {
        return nombre;
    }
       
}
