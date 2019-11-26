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
public abstract class Sorpresa {
       
    protected String texto;
    protected int valor;
    
    protected MazoSorpresas mazo;
    protected Tablero tablero;
    public Diario diario = Diario.getInstance() ;

    
    protected void init(){
        this.valor = -1;
        this.tablero = null;
        this.mazo = null;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return ( actual >= 0 && actual < todos.size() );
    }
    
    void informe (int actual, ArrayList<Jugador> todos){
        if (jugadorCorrecto(actual, todos))
            diario.ocurreEvento("Se ha aplicado una sorpresa de tipo " + this.getClass().getSimpleName() + " a " + todos.get(actual).getNombre());
    }
    
    void aplicarAJugador( int actual, ArrayList<Jugador> todos ){
        if (jugadorCorrecto(actual, todos))
            informe(actual, todos);

       /*     switch (tipo){
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
        }*/
    }

    @Override
    public String toString() {
        return "\n---Sorpresa---" + "\n" + texto + "\nValor: " + valor + "\nTipo: " + this.getClass().getSimpleName() + "\n";
    }
    
    
}
