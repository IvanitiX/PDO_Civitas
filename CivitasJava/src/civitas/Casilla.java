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

    public Casilla(String nombre, float importe) {
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
            diario.ocurreEvento(todos.get(actual).getNombre()+ " ha llegado a la casilla:\n" + toString());
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return ( actual >= 0 && actual < todos.size() );
    }
    
    public String toString(){
        String s = "Nombre: " + nombre + "\nTipo: " + tipo;
        if(tipo==TipoCasilla.CALLE){
            s += "\n" + titulo.toString() ;
        }
        if(tipo==TipoCasilla.IMPUESTO){
            s+= "\nImporte del impuesto: " + importe + "\n" ;
        }
        if(tipo==TipoCasilla.JUEZ){
            s+= "\nEl jugador será enviado a la cárcel\n" ;
        }
        if(tipo==TipoCasilla.SORPRESA){
            s+= "\nLa sorpresa que saldrá es:\n" + sorpresa.toString() ;
        }
        if ("Carcel".equals(nombre) ){
            s+= "\nBienvenido a la carcel\n";
        }
        else{
            s+= "\nEstas descansando...";
        }
        return s ;
    }
    
    void recibeJugador(int actual, ArrayList<Jugador> todos){
        switch (tipo){
            case CALLE:
                this.recibeJugador_calle(actual, todos);
            break ;
            case IMPUESTO:
                this.recibeJugador_impuesto(actual, todos);
            break ;
            case JUEZ:
                this.recibeJugador_juez(actual, todos);
            break ;
            case SORPRESA:
                this.recibeJugador_sorpresa(actual, todos);
            break ;
            default:
                this.informe(actual, todos);
            break;
        }
    }
    
    private void recibeJugador_impuesto(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            todos.get(actual).pagaImpuesto(importe) ;
        }
    }
    
    private void recibeJugador_juez(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            todos.get(actual).encarcelar(carcel) ;
        }
    }
    
    private void recibeJugador_calle(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
           this.informe(actual, todos);
           //Jugador jugador = new Jugador (todos.get(actual)) ;
           Jugador jugador = todos.get(actual) ;
           if(!titulo.tienePropietario()){
               jugador.puedeComprarCasilla() ;
           }
           else{
               titulo.tramitarAlquiler(jugador);
           }
        }
    }
    
    private void recibeJugador_sorpresa(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
           sorpresa = mazo.siguiente() ;
           this.informe(actual, todos);
           sorpresa.aplicarAJugador(actual, todos);
        }
    }

    public TituloPropiedad getTitulo() {
        return titulo;
    }
    
    
    String getNombre() {
        return nombre;
    }
       
}
