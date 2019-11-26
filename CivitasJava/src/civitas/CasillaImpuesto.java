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
public class CasillaImpuesto extends Casilla {
    private float importe ;
    
    public CasillaImpuesto (String nombre, float importe) {
        super(nombre) ;
        this.importe = importe;
    }
    
    @Override
    public String toString(){
        String s = super.toString() ;
        s += s+= "\nImporte del impuesto: " + importe + "\n" ;
        return s;
    }
    
    @Override
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(super.jugadorCorrecto(actual,todos)){
            super.informe(actual,todos) ;
            todos.get(actual).pagaImpuesto(importe) ;
        }
    }
}
