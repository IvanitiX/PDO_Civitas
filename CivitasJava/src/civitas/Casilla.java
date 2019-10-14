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
    private TipoCasilla tipo ;
    private Diario diario = Diario.getInstance() ;
    
    public Casilla(TituloPropiedad titulo) {
        init() ;
        this.titulo = titulo;
        this.nombre = titulo.getNombre() ;
        this.importe = titulo.getPrecioCompra() ;
        this.tipo = TipoCasilla.CALLE ;
    }

    public Casilla(int carcel, String nombre) {
        init() ;
        this.carcel = carcel;
        this.nombre = nombre ;
        this.tipo = TipoCasilla.JUEZ ;
    }

    public Casilla(MazoSorpresas mazo, String nombre) {
        init() ;
        this.nombre = nombre;
        this.mazo = mazo;
        this.tipo = TipoCasilla.SORPRESA ;
        this.sorpresa = mazo.siguiente() ;
    }

    public Casilla(float importe, String nombre) {
        init() ;
        this.nombre = nombre;
        this.importe = importe;
        this.tipo = TipoCasilla.IMPUESTO ;
    }

    Casilla (String nombre){
        init() ;
        this.nombre = nombre ;
        this.tipo = TipoCasilla.DESCANSO ;
    }
    
    private void init(){
        nombre = "";
        importe = (float) 0.0;
        titulo = null ;
        sorpresa = null ;
        mazo = null;
        tipo = null ;
    }
    
    private TituloPropiedad getTituloPropiedad(){
        if (tipo == TipoCasilla.CALLE)
            return titulo;
        else
            return null ;
    }
    
    void informe (int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos))
            diario.ocurreEvento(todos.get(actual).getNombre()+ "ha llegado a la casilla " + toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return ( actual >= 0 && actual < todos.size() );
    }
    
    public String to_s(){
        String s = "Casilla \n Nombre: " + nombre + "\n Tipo: " + tipo;
        if(tipo==TipoCasilla.CALLE){
            s += "\n" + titulo.toString() ;
        }
        if(tipo==TipoCasilla.IMPUESTO){
            s+= "\n Importe del impuesto: " + importe ;
        }
        if(tipo==TipoCasilla.JUEZ){
            s+= "\n El jugador será enviado a la cárcel" ;
        }
        if(tipo==TipoCasilla.SORPRESA){
            s+= "\n La sorpresa que saldrá es : " + sorpresa.toString() ;
        }
        return s ;
    }
    
    void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            todos.get(actual).pagaImpuesto(importe) ;
        }
    }
    
    void recibeJugador_juez(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            todos.get(actual).encarcelar(carcel) ;
        }
    }
    
    
    String getNombre() {
        return nombre;
    }
       
}
