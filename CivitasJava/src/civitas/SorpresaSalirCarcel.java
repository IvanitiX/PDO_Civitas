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
public class SorpresaSalirCarcel extends Sorpresa {
     SorpresaSalirCarcel (MazoSorpresas mazo){
        this.init();
        this.mazo = mazo;
        this.texto = "Dejas un cheque al juez en una cuenta sin fondos y se lo ha creído.";
    }
     
     @Override
    void aplicarAJugador (int actual, ArrayList<Jugador> todos ){
        super.aplicarAJugador(actual, todos);
        boolean alguien = false;
        for (Jugador j: todos){
            if (j.tieneSalvoconducto())
                alguien = true;
        }
        
        if (!alguien){
            todos.get(actual).obtenerSalvoconducto(new SorpresaSalirCarcel(mazo));
            salirDelMazo();
        }
    }
    
    void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }

    void usada(){
        mazo.habilitarCartaEspecial(this);
    }
    
}
