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
public class Tablero {
    private int numCasillaCarcel ;
    private ArrayList<Casilla> casillas ;
    private int porSalida ;
    private boolean tieneJuez;
    
    Tablero (int carcel){
        if (carcel >= 1)
            numCasillaCarcel = carcel ;
        else
            numCasillaCarcel = 1 ;
        casillas = new ArrayList<Casilla>() ;
        casillas.add(new Casilla("Salida")) ;
        porSalida = 0 ;
        tieneJuez = false ;
    }
    
    private boolean correcto(){
        return (tieneJuez && casillas.size() >= numCasillaCarcel) ;
    }
    
    private boolean correcto(int numCasilla){
        boolean enTablero = numCasilla >= 0 && numCasilla < casillas.size() ;
        return this.correcto() && enTablero ;
    }

    int getCarcel() {
        return numCasillaCarcel;
    }

    Casilla getCasilla(int numCasilla) {
        if(correcto(numCasilla))
           return casillas.get(numCasilla) ;
        else
            return null ;
    }

    int getPorSalida() {
        int aDevolver = porSalida ;
        if (porSalida > 0){
            porSalida-- ;
        }
        return aDevolver ;
    }
    
    void aniadeCasilla(Casilla casilla){
        if (casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
        }
        
        casillas.add(casilla);
        
        if (casillas.size() == numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
        }
    }
    
    void aniadeJuez(){
        if(!tieneJuez){
            aniadeCasilla(new CasillaJuez(numCasillaCarcel, "Juez"));
            tieneJuez = true;
        }
    }
    
    int nuevaPosicion(int actual,int tirada){
        if(!correcto())
            return -1;
        else{
            int posicion = actual+tirada ;
            if(posicion >= casillas.size()){
                porSalida++ ;
            }
            return (posicion % casillas.size()) ;
        }
    }
    
    int calcularTirada(int origen, int destino){
        int resta = destino-origen ;
        if (resta < 0){
            resta += casillas.size() ;
        }
        return resta ;
    }
    
    
}
