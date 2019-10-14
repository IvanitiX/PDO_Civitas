/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList ;

/**
 *
 * @author ivan
 */
public class Sorpresa {
       
    private String texto;
    private int valor;
    
    private TipoSorpresa tipo;
    private MazoSorpresas mazo;
    private Tablero tablero;
    public Diario diario = Diario.getInstance() ;

    //Carcel
    Sorpresa (TipoSorpresa tipo, Tablero tablero){
        this.init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.texto = "Te han pillado dando galletas Oreo rellenas de pasta de dientes.";
        
    }
    
    //Enviar jugador a otra casilla
    Sorpresa (TipoSorpresa tipo, Tablero tablero, int valor, String texto ){
        this.init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
        
    }
    
    //Resto
    Sorpresa (TipoSorpresa tipo, int valor, String texto ){
        this.init();
        this.tipo = tipo;
        this.valor = valor;
        this.texto = texto;
        
    }
    
    //Salir carcel
    Sorpresa (TipoSorpresa tipo, MazoSorpresas mazo){
        this.init();
        this.tipo = tipo;
        this.mazo = mazo;
        this.texto = "Dejas un cheque al juez en una cuenta sin fondos y se lo ha creído.";
    }
    
    private void init(){
        this.valor = -1;
        this.tablero = null;
        this.mazo = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return ( actual >= 0 && actual < todos.size() );
    }
    
    void informe (int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos))
            diario.ocurreEvento("Se ha aplicado una sorpresa de tipo" + tipo + " a" + todos.get(actual).getNombre());
    }
    
    /* SUPONDREMOS QUE NO SE LLAMA A LOS METODOS DE ABAJO SUELTOS <3 */
    void aplicarAJugador( int actual, ArrayList<Jugador> todos ){
        if (jugadorCorrecto(actual, todos)){
            informe(actual, todos);

            switch (tipo){
                case IRCARCEL:
                    aplicarAJugador_irCarcel(actual,todos);
                    break;
                case IRCASILLA:
                    aplicarAJugador_irACasilla(actual,todos);
                    break;
                case PAGARCOBRAR:
                    aplicarAJugador_pagarCobrar(actual,todos);
                    break;
                case SALIRCARCEL:
                    aplicarAJugador_salirCarcel(actual,todos);
                    break;
                case PORCASAHOTEL:
                    aplicarAJugador_porCasaHotel(actual,todos);
                    break;
                case PORJUGADOR:
                    aplicarAJugador_porJugador(actual,todos);
                    break;
            }
        }
    }
    
    private void aplicarAJugador_irCarcel (int actual, ArrayList<Jugador> todos ){
        
        todos.get(actual).encarcelar( tablero.getCarcel() );
        
    }
    
    private void aplicarAJugador_irACasilla (int actual, ArrayList<Jugador> todos ){
                
        int numCasilla = todos.get(actual).getNumCasillaActual();
        
        int numTirada = tablero.calcularTirada(numCasilla, valor);
        
        int nuevaPosicion = tablero.nuevaPosicion(numCasilla, numTirada);
        
        todos.get(actual).moverACasilla(nuevaPosicion);
        
        numCasilla = todos.get(actual).getNumCasillaActual();
        
        Casilla casilla = tablero.getCasilla(numCasilla);
        
        //casilla.recibeJugador(actual,todos);
        
    }
    
    private void aplicarAJugador_pagarCobrar (int actual, ArrayList<Jugador> todos ){
                        
        todos.get(actual).modificarSaldo(valor);
        
    }
    
    private void aplicarAJugador_salirCarcel (int actual, ArrayList<Jugador> todos ){
        boolean alguien = false;
        for (Jugador j: todos){
            if (j.tieneSalvoconducto())
                alguien = true;
        }
        
        if (!alguien){
            todos.get(actual).obtenerSalvoconducto(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
            salirDelMazo();
        }
    }
    
    private void aplicarAJugador_porCasaHotel (int actual, ArrayList<Jugador> todos ){
        
        Jugador jugador = todos.get(actual);
        
        jugador.modificarSaldo ( valor * jugador.cantidadCasasHoteles() );
    }
    
    private void aplicarAJugador_porJugador (int actual, ArrayList<Jugador> todos ){
        
        Sorpresa s = new Sorpresa (TipoSorpresa.PAGARCOBRAR, -valor, "Se dan " + valor + " al jugador que la use") ;
        Sorpresa j = new Sorpresa (TipoSorpresa.PAGARCOBRAR, 3*valor, "Se recibe " + 3*valor + " del resto de jugadores") ;
        
        for (int i=0; i < todos.size(); i++){
            
            if (i != actual){
                s.aplicarAJugador(i, todos);
            }
            else{
                j.aplicarAJugador(i, todos) ;
            }
            
            
        }
    }
    
    void salirDelMazo(){
        if (tipo == TipoSorpresa.SALIRCARCEL){
                mazo.inhabilitarCartaEspecial(this);
            }
            
        }

    void usada(){
        if(tipo == TipoSorpresa.SALIRCARCEL)
            mazo.habilitarCartaEspecial(this);
    }

    @Override
    public String toString() {
        return "Sorpresa{" + "texto=" + texto + ", valor=" + valor + ", tipo=" + tipo + ", mazo=" + mazo + ", tablero=" + tablero + ", diario=" + diario + '}';
    }
    
    
}
