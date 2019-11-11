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
public class TituloPropiedad {
    private String nombre;
    private float alquilerBase ;
    private static final float factorInteresesHipoteca = (float) 1.1 ;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private boolean hipotecado ;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario ;
    

    public TituloPropiedad(String nombre, float alquilerBase, float factorRevalorizacion, 
            float hipotecaBase,int precioCompra, int precioEdificar) {
        this.nombre = nombre;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.hipotecado = false;
        this.numCasas = 0;
        this.numHoteles = 0;
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.propietario = null;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "nombre=" + nombre + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", hipotecado=" + hipotecado + ", numCasas=" + numCasas + ", numHoteles=" + numHoteles + ", precioCompra=" + precioCompra + ", precioEdificar=" + precioEdificar + ", propietario=" + propietario + '}' + "\n";
    }
    

    
    private float getPrecioAlquiler(){
        float alquiler = 0 ;
        if (!hipotecado && !propietarioEncarcelado())
            alquiler = alquilerBase*(1+(numCasas*(float)0.5)+(numHoteles*(float)2.5)) ;
        return alquiler;
    }
    
    float getImporteCancelarHipoteca(){
        return getImporteHipoteca() * factorInteresesHipoteca ;
    }

    boolean cancelarHipoteca(Jugador jugador){
        boolean hecho = false ;
        if(esEsteElPropietario(jugador) && hipotecado){
            if (jugador.paga(getImporteCancelarHipoteca())){
                hipotecado = false ;
                hecho = true;
            }
        }
        return hecho ;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean hecho = false ;
        if(!hipotecado && esEsteElPropietario(jugador)){
            jugador.recibe(getImporteHipoteca());
            hipotecado = true ;
            hecho = true ;
        }
        return hecho ;
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
            jugador.pagaAlquiler(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler()) ;
        }
    }
    
    private boolean propietarioEncarcelado(){
        if (tienePropietario())
            return propietario.isEncarcelado() ;
        else
            return false ;
    }
    
    int cantidadCasasHoteles(){
        return numCasas + numHoteles ;
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        boolean hecho = false ;
        if(esEsteElPropietario(jugador) && numCasas >= n){
            numCasas -= n ;
            hecho = true ;
        }
        return hecho ;
    }
    
    private float getPrecioVenta(){
        float precioVenta =precioCompra+(numCasas+5*numHoteles)*precioEdificar*factorRevalorizacion ;
        return precioVenta ;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean construida = false ;
        if (esEsteElPropietario(jugador)){
            if(jugador.paga(precioEdificar)){
                numCasas++;
                construida = true ;
            }
        }
        return construida ;
    }
    
    boolean construirHotel(Jugador jugador){
        boolean construido = false ;
        if (esEsteElPropietario(jugador)){
            jugador.paga(3*getPrecioEdificar());
            numHoteles++;
            construido = true ;
            
        }
        return construido ;
    }
    
    boolean Comprar(Jugador jugador){
        boolean comprado = false ;
        if (!tienePropietario()){
            propietario = jugador ;
            comprado = true ;
            jugador.paga(precioCompra) ;
        }
        return comprado ;
    }
    
    boolean tienePropietario(){
        return propietario != null ;
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return propietario == jugador ;
    }

    String getNombre() {
        return nombre;
    }

    int getNumCasas() {
        return numCasas;
    }

    int getNumHoteles() {
        return numHoteles;
    }

    float getPrecioCompra() {
        return precioCompra;
    }

    float getPrecioEdificar() {
        return precioEdificar;
    }

    Jugador getPropietario() {
        return propietario;
    }
    
    public boolean getHipotecada(){
        return hipotecado ;
    }
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        throw new UnsupportedOperationException("No implementado") ;
    }
    
    private float getImporteHipoteca(){
        return (float) (hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }
    
    boolean vender(Jugador jugador){
        jugador.modificarSaldo(getPrecioVenta()) ;
        this.derruirCasas(getNumCasas(), jugador) ;
        this.numHoteles = 0 ;
        this.propietario=null;
        return false ;
    }
    
    
    
    
    
}
