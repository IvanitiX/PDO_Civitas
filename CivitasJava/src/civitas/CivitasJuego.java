/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Miguel Muñoz Molina
 * @author Iván Valero Rodríguez
 */
public class CivitasJuego {
    private int indiceJugadorActual ;
    private MazoSorpresas mazo ;
    private EstadosJuego estado;
    private GestorEstados gestorEstados ;
    private Tablero tablero ;
    private ArrayList<Jugador> jugadores ;
    private Dado dado = Dado.getInstance() ;
    private static final int CASILLA_CARCEL = 15 ;
    private OperacionesJuego operacion;

    public CivitasJuego(ArrayList<String> nombres) {
        jugadores = new ArrayList<>() ;
        for(int i = 0 ; i < nombres.size() ; i++){
            System.out.println(i + "  " + nombres.get(i));
            jugadores.add(new Jugador(nombres.get(i))) ;
        }
       
        gestorEstados = new GestorEstados() ;
        estado = gestorEstados.estadoInicial() ;
        indiceJugadorActual = dado.quienEmpieza(jugadores.size()) ;
        tablero = new Tablero(CASILLA_CARCEL) ;
        mazo = new MazoSorpresas() ;       
        inicializarMazo(tablero) ;
        inicializarTablero(mazo) ;
        
    }
    
    public boolean finalDelJuego(){
        boolean gameOver = false ;
        for (Jugador j: jugadores){
            if(j.enBancarrota()){
                gameOver = true ;
            }
        }
        return gameOver ;
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip );
    }
    
    boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip) ;
    }
    
    boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip) ;
    }
    
    boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip) ;
    }
    
    boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip) ;
    }
    
    boolean salirCarcelPagando(){
         return jugadores.get(indiceJugadorActual).salirCarcelPagando() ;
    }
    
    boolean salirCarcelTirando(){
         return jugadores.get(indiceJugadorActual).salirCarcelTirando() ;
    }
    
    ArrayList<Jugador> ranking(){
        Collections.sort(jugadores);
        return jugadores;
    }
    
    Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual()) ;
    }
    
    Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual) ;
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Calderón de la Barca", 200, 50, (float) 0.1, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Montilla", 300, 60, (float) 0.2, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(mazo,"¡Sorpresa!")) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Platón", 400, 50, (float) 0.0, 300, 100))) ;
        tablero.aniadeJuez() ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Ntra.Sra de Tíscar", 200, 70, (float) 0.1, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(200, "Zona azul")) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("José de Mora", 300, 50, (float) -0.1, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Baza", 400, 60, (float) 0.2, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla("Bar La Posada")) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Juan de Echevarría", 200, 50, (float) 0.2, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(mazo,"¡Sorpresa!")) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("San León", 300, 70, (float) 0.1, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Vía Láctea", 400, 80, (float) -0.2, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Sierra Morena", 200, 80, (float) 0.1, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(mazo,"¡Sorpresa!")) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Casillas", 300, 50, (float) 0.2, 300, 100))) ;
        tablero.aniadeCasilla(new Casilla(new TituloPropiedad("Francisco Pradilla", 400, 60, (float) 0.0, 300, 100))) ;
    }
    
    private void inicializarMazo(Tablero tablero){
        mazo.alMazo (new Sorpresa (TipoSorpresa.PAGARCOBRAR, 500, "El Gobierno te ha dado una subvención por tus propiedades.")) ;
        mazo.alMazo (new Sorpresa (TipoSorpresa.PAGARCOBRAR, -500, "Pagas penalización por pasarte de potencia eléctrica.")) ;
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, 0, "Ve a la salida y cobra antes que nadie (o no).")) ;
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));
        mazo.alMazo (new Sorpresa (TipoSorpresa.IRCASILLA, tablero, 10, "Después de salir con el runner, ve a La Posada y descansa.")) ;
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORCASAHOTEL, 100, "El Gobierno te extiende una transferencia para mejorar tus edificios.")) ;
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORCASAHOTEL, 100, "Hacienda te pide bajo pena de prisión que pagues tus tributos.")) ;
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORJUGADOR, 100, "¡Es tu cumpleaños! Como no te han comprado regalos, te darán dinero.")) ;
        mazo.alMazo (new Sorpresa (TipoSorpresa.PORJUGADOR, 100, "Le has roto el coche a los 3  y debes compensarles.")) ;
    }
    
    public String infoJugadorTexto(){
        return getJugadorActual().toString() ;
    }
    
    private void pasarTurno(){
        indiceJugadorActual = (indiceJugadorActual+1)%jugadores.size() ;
    }
    
    void siguientePasoCompletado(OperacionesJuego operacion){
        estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion) ;
    }
    
    void actualizarInfo(){
        System.out.println(infoJugadorTexto()) ;
        if(getJugadorActual().enBancarrota()){
            System.out.println(ranking().toString()) ;
        }
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida() ;
        }
    }

    public int getIndiceJugadorActual() {
        return indiceJugadorActual;
    }

    public MazoSorpresas getMazo() {
        return mazo;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    
    
    
}
