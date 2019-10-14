/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 * @author Miguel Muñoz Molina
 * @author Iván Valero Rodríguez
 */
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Dado prueba = Dado.getInstance() ;
        Diario diario = Diario.getInstance() ;
        
        System.out.println("Test 1 : ¿Quien empieza?");
        for(int i = 0 ; i < 100 ;  i++){
         System.out.println(prueba.quienEmpieza(4)) ;
        }
        
        System.out.println("_____________________________________________\n\n");
        
        System.out.println("Test 2 : Probando el dado");
        for(int i = 0 ; i < 10 ; i++){
            prueba.tirar() ;
            int test = prueba.getUltimoResultado();
            System.out.println(test) ;
            prueba.setDebug(true);
            prueba.tirar();
            test = prueba.getUltimoResultado();
            System.out.println(test) ;
            prueba.setDebug(false);
            if(prueba.salgoDeLaCarcel())
             System.out.println("Saqué un " + prueba.getUltimoResultado() + ". Podría salir de la carcel");
        }
                
        System.out.println("_____________________________________________\n\n");
        
        System.out.println("Test 3 : Elementos de un enumerado");
        System.out.println(TipoSorpresa.IRCARCEL);
        System.out.println(TipoCasilla.CALLE);
        System.out.println(EstadosJuego.INICIO_TURNO);
        
                
        System.out.println("_____________________________________________\n\n");
        
        System.out.println("Test 4 : Mazo Sorpresas y Diario");
        MazoSorpresas mazo = new MazoSorpresas(true);
        Sorpresa s1,s2 ;
        s1 = new Sorpresa (TipoSorpresa.PAGARCOBRAR, 500, "El Gobierno te ha dado una subvención por tus propiedades.") ;
        s2 = new Sorpresa (TipoSorpresa.PAGARCOBRAR, -500, "El Gobierno te ha dado una jodienda por tus propiedades.") ;
        mazo.alMazo (s1) ;
        mazo.alMazo (s2) ;
        mazo.siguiente() ;
        mazo.inhabilitarCartaEspecial(s2);
        mazo.habilitarCartaEspecial(s2);
        while(diario.eventosPendientes()){
            System.out.println(diario.leerEvento()) ;
        }
        
        System.out.println("_____________________________________________\n\n");
        
        System.out.println("Test 5 : Tablero");
        Tablero tablero = new Tablero(2);
        System.out.println(tablero.nuevaPosicion(prueba.getUltimoResultado(), prueba.tirar())) ;
        tablero.aniadeJuez() ;
        tablero.aniadeCasilla(new Casilla("Prueba"));
        System.out.println (tablero.toString()) ;
        for(int i = 0 ; i < 10 ; i++)
        System.out.println(tablero.nuevaPosicion(prueba.getUltimoResultado(), prueba.tirar())) ;
        
        
    }
    
}
