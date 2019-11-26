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
public class SorpresaJugador extends Sorpresa {
    SorpresaJugador (int valor, String texto ){
        this.init();
        this.valor = valor;
        this.texto = texto;
        
    }    
    
    @Override
        void aplicarAJugador (int actual, ArrayList<Jugador> todos ){
        super.aplicarAJugador(actual, todos);
        
        SorpresaPagarCobrar s = new SorpresaPagarCobrar (-valor, "Se dan " + valor + " al jugador que la use") ;
        SorpresaPagarCobrar j = new SorpresaPagarCobrar (3*valor, "Se recibe " + 3*valor + " del resto de jugadores") ;
        
        for (int i=0; i < todos.size(); i++){
            
            
            if (i != actual){
                s.aplicarAJugador(i, todos);
            }
            else{
                j.aplicarAJugador(i, todos) ;
            }
            
            
        }
    }
}
