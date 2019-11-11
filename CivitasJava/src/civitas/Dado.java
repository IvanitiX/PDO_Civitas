/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.Random;

/**
 *
 * @author ivanva20
 */
public class Dado {
    //Clase Singleton
    static final private Dado instance = new Dado();
    static private int salidaCarcel = 5 ;
    
    private Random random ;
    private int ultimoResultado ;
    private boolean debug ;
    
    public static Dado getInstance() {
        return instance;
    }
    
    private Dado () {
     debug = false ;
     random = new Random() ;
     ultimoResultado = 0 ;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getUltimoResultado() {
        return ultimoResultado;
    }
    
    int tirar(){
        int tirada = 1;
        if (!debug){
            tirada = (int)(random.nextDouble()*6 + 1) ;
        }
        ultimoResultado = tirada ;
        
        return tirada ;
    }
    
    boolean salgoDeLaCarcel (){
        return tirar() == salidaCarcel ;
    }
    
    int quienEmpieza (int n){
        return (int)(random.nextDouble()*n) ;
    }
}
