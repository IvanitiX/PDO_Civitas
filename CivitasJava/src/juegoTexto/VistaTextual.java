package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Jugador;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import civitas.TituloPropiedad;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  public VistaTextual () {
    in = new Scanner (System.in);
  }
  
  public void mostrarEstado(String estado) {
    System.out.println("<<-------------Estado de Juego---------------");
    System.out.println (estado);
    System.out.println("---------------Estado de Juego------------->>");

  }
              
  public void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  public int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  public SalidasCarcel salirCarcel() {
    System.out.println("<<-------------Salir Carcel---------------");
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    System.out.println("---------------Salir Carcel------------->>");
    return (SalidasCarcel.values()[opcion]);
  }

  public Respuestas comprar() {
    System.out.println("<<-------------Comprar---------------");
    int opcion = menu ("Elige si quieres o no comprar la calle en la que estas situado",
    new ArrayList<> (Arrays.asList("NO","SI")));
    System.out.println("---------------Comprar------------->>");
    return (Respuestas.values()[opcion]);
  }

  public void gestionar () {
    System.out.println("<<-------------Gestionar---------------");
    int opcion1 = menu ("¿Que numero de gestion inmobiliaria deseas hacer?",
    new ArrayList<> (Arrays.asList("VENDER", "HIPOTECAR", "CANCELAR_HIPOTECA", "CONSTRUIR_CASA", "CONSTRUIR_HOTEL", "TERMINAR")));
    
    ArrayList<TituloPropiedad> propiedades = juegoModel.getJugadorActual().getPropiedades();
    ArrayList<String> nombres = new ArrayList<String>();
    
    for (TituloPropiedad p:propiedades){
        nombres.add(p.toString());
    }   

    if (juegoModel.getJugadorActual().getPropiedades().size() != 0 && opcion1 != 5){
    
        int opcion2 = menu("¿Que propiedad quieres gestionar?",nombres);
        iPropiedad = opcion2;
        iGestion = opcion1;
    }else{
        iGestion=5;
    }
    
    if (juegoModel.getJugadorActual().getPropiedades().size() == 0)
        System.out.println("!No es por nada, pero... ¿no crees que no tiene sentido que puedas gestionar algo que no existe? (Error 404)");

    System.out.println("---------------Gestionar------------->>");
    
  }
  
  public int getGestion(){
      return iGestion;
  }
  
  public int getPropiedad(){
      return iPropiedad;
  }
    

  public void mostrarSiguienteOperacion(OperacionesJuego operacion) {
    System.out.println("<<-------------Operaciones Juego---------------");

      System.out.println(operacion);
    System.out.println("---------------Operaciones Juego------------->>");
  }


  public void mostrarEventos() {
      System.out.println("<<-------------Evento Diario---------------");
      while (Diario.getInstance().eventosPendientes()){
          System.out.println(">> " + Diario.getInstance().leerEvento());
      }
      System.out.println("---------------Evento Diario------------->>");
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();

    }
  
  public void actualizarVista(){
      System.out.println("<<-------------Jugador Actual---------------");
      System.out.println( juegoModel.getJugadorActual().toString() );
      System.out.println("---------------Jugador Actual------------->>\n");
      System.out.println("<<-------------Casilla Actual---------------");
      System.out.println( juegoModel.getCasillaActual().toString() );
      System.out.println("---------------Casilla Actual------------->>\n");
  } 
}
