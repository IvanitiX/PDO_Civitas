/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;

/**
 *
 * @author Usuario
 */
public class Controlador {
    private CivitasJuego juego;
    private CivitasView vista;
    
    Controlador(CivitasJuego juego, CivitasView vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        
        while (!juego.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego op = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(op);
            
            
            if (op != OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            
            if (!juego.finalDelJuego()){
                switch(op){
                    case COMPRAR:
                        if (vista.comprar() == Respuestas.SI){
                            juego.comprar();
                        }
                        juego.siguientePasoCompletado(op);
                        break;
                        
                    case GESTIONAR:
                        vista.gestionar();
                        int iGestion = vista.getGestion();
                        int iPropiedad = vista.getPropiedad();
                       
                        OperacionInmobiliaria opInmobiliaria = 
                                new OperacionInmobiliaria(GestionesInmobiliarias.values()[iGestion], iPropiedad);
                        
                        switch(opInmobiliaria.getGestion()){
                            case VENDER:
                                juego.vender(iPropiedad);
                                break;
                            case HIPOTECAR:
                                juego.hipotecar(iPropiedad);
                                break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(iPropiedad);
                                break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(iPropiedad);
                                break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(iPropiedad);
                                break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(op);
                                break; 
                        }
                        break;
                        
                    case SALIR_CARCEL:
                        if (vista.salirCarcel() == SalidasCarcel.PAGANDO){
                            juego.salirCarcelPagando();
                        } else{
                            juego.salirCarcelTirando();
                        }
                        juego.siguientePasoCompletado(op);
                        break;
                        
                        
                        
                        
                }
            }
            
        }
        juego.ranking();
        
    }
}
