/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import GUI.Dado ;

/**
 * @author Miguel Muñoz Molina
 * @author Iván Valero Rodríguez
 */

public class Jugador implements Comparable<Jugador> {
    protected static int CasasMax = 4 ;
    protected static int HotelesMax = 4 ;
    protected static int CasasPorHotel = 4 ;
    protected static float PasoPorSalida = 1000 ;
    protected static float SaldoInicial = 7500 ;
    protected static float PrecioLibertad = 200 ;
    private String nombre ;
    private int numCasillaActual ;
    private float saldo ;
    private boolean puedeComprar ;
    protected boolean encarcelado ;
    private Diario diario = Diario.getInstance() ;
    private SorpresaSalirCarcel salvoconducto ;
    protected ArrayList<TituloPropiedad> propiedades ;
    private Dado dado = Dado.getInstance() ;

    Jugador(String nombre) {
        this.nombre = nombre;
        this.encarcelado = false;
        this.numCasillaActual = 0 ;
        this.puedeComprar = false ;
        this.saldo = SaldoInicial ;
        salvoconducto = null;
        propiedades = new ArrayList<>() ;
    }

    protected Jugador(Jugador otro) {
        this.nombre = otro.nombre;
        this.numCasillaActual = otro.numCasillaActual;
        this.saldo = otro.saldo;
        this.puedeComprar = otro.puedeComprar;
        this.encarcelado = otro.encarcelado ;
        this.salvoconducto = otro.salvoconducto;
        this.propiedades = otro.propiedades ;
    }

    protected static int getCasasMax() {
        return CasasMax;
    }

    protected static int getHotelesMax() {
        return HotelesMax;
    }

    static int getCasasPorHotel() {
        return CasasPorHotel;
    }

    private static float getPremioPasoPorSalida() {
        return PasoPorSalida;
    }

    private static float getPrecioLibertad() {
        return PrecioLibertad;
    }

    public String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual;
    }

    public float getSaldo() {
        return saldo;
    }
    
    public boolean isEspeculador(){
        return false;
    }

    boolean getPuedeComprar() {
        return puedeComprar;
    }

    public boolean isEncarcelado() {
        return encarcelado;
    }
    
    @Override
    public int compareTo(Jugador otro){
        return (int) Float.compare(otro.saldo, saldo) ;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel) ;
            encarcelado = true ;
            diario.ocurreEvento(nombre + " va a ir a la cárcel.");
        }
        return encarcelado ;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean carcel = false;
        if(tieneSalvoconducto()){
            perderSalvoconducto() ;
        }
        if(!tieneSalvoconducto() && !encarcelado){
            carcel =true ;
        }
        return carcel ;
    }
    
    boolean moverACasilla(int numCasilla){
        if (encarcelado)
            return false ;
        else{
            numCasillaActual = numCasilla ;
            puedeComprar = false; 
            diario.ocurreEvento("El jugador " + nombre + " se ha movido de casilla.");
            return true ;
        }
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad ;
        return true;
    }
    
    boolean tieneSalvoconducto(){
        return salvoconducto != null ;
    }

    @Override
    public String toString() {
        return "Jugador{" + "nombre=" + nombre + ", numCasillaActual=" + numCasillaActual + ", saldo=" + saldo + ", puedeComprar=" + puedeComprar + ", encarcelado=" + encarcelado + ", diario=" + diario + ", salvoconducto=" + salvoconducto + '}';
    }
    
    boolean puedeComprarCasilla(){
        puedeComprar = !encarcelado;
        return puedeComprar;
    }
    
    boolean enBancarrota(){
        return saldo <= 0 ;
    }
    
    boolean tieneAlgoQueGestionar(){
        return propiedades!=null ;
    }

    void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null ;
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        if (encarcelado)
            return false ;
        else{
            salvoconducto = sorpresa;
            return true;
        }     
    }
    
    boolean puedeSalirCarcelPagando(){
        return saldo >= PrecioLibertad ;
    }
    
    boolean salirCarcelPagando(){
        if(puedeSalirCarcelPagando()){
            paga(PrecioLibertad) ;
            diario.ocurreEvento(nombre + " ha pagado la fianza y sale de la cárcel.\n");
            encarcelado = false ;
            return true ;
        }
        else return false ;
    }
    
    boolean salirCarcelTirando(){
        if (dado.salgoDeLaCarcel()){
            encarcelado = false ;
            diario.ocurreEvento(nombre + " ha salido de la cárcel por el dado.\n");
        }
        return encarcelado ;
    }
    
    boolean puedoGastar(float precio){
        if (encarcelado) return false ;
        else return saldo >= precio ;
    }
    
    boolean recibe (float cantidad){
        if (encarcelado) return false;
        else return modificarSaldo(cantidad) ;
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(-cantidad) ;    
    }
    
    boolean pagaImpuesto(float cantidad){
        if (encarcelado) return false;
        else{
            return paga(cantidad) ;
        }
    }
    
    boolean pagaAlquiler(float cantidad){
        if (encarcelado) return false;
        else return paga(cantidad) ;
    }
    
    boolean pasaPorSalida(){
        return modificarSaldo(PasoPorSalida);
    }
    
    boolean vender(int ip){
        if (encarcelado) return false ;
        else{
            if (existeLaPropiedad(ip)){
                propiedades.get(ip).vender(this) ;
                propiedades.remove(ip);
                return true ;
            }
            else return false;
        }
    }
    
    boolean existeLaPropiedad(int ip){
        return (ip < propiedades.size()) ;
    }
    
    boolean puedoEdificarCasa(TituloPropiedad titulo){
        return titulo.getNumCasas() < this.getCasasMax() && puedoGastar(titulo.getPrecioEdificar()) ;
    }
    
    boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puedoEdificarHotel = false ;
        float precio = titulo.getPrecioEdificar();
        if (this.puedoGastar(precio) && titulo.getNumHoteles() < this.getHotelesMax() 
                && titulo.getNumCasas() >= this.getCasasPorHotel())
            puedoEdificarHotel = true ;
        return puedoEdificarHotel;
    }
    
    int cantidadCasasHoteles(){
        int edificios = 0 ;
        for(int i = 0 ;i < propiedades.size(); i++){
            edificios += propiedades.get(i).cantidadCasasHoteles() ;
        }
        return edificios ;
    }
    
     boolean construirCasa(int ip){
        boolean result = false ;
        if (this.isEncarcelado()){
            return result ;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip) ;
            boolean puedoEdificarCasa = this.puedoEdificarCasa(propiedad) ;
            if (puedoEdificarCasa){
                result = propiedad.construirCasa(this) ;
                if (result){
                    diario.ocurreEvento("El jugador " + getNombre() + " construye casa en la propiedad " + ip);
                }
            }
        }
        return result ;
    }
    
     boolean construirHotel(int ip){
        boolean result = false ;
        if (this.isEncarcelado()){
            return result ;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip) ;
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad) ;
            if (puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                int casasPorHotel = this.getCasasPorHotel() ;
                propiedad.derruirCasas(casasPorHotel, this) ;
            }
            diario.ocurreEvento("El jugador " + getNombre() + " construye hotel en la propiedad " + ip);
        }
        return result ;
    }
     
     boolean hipotecar(int ip){
        boolean result = false ;
        if (this.isEncarcelado()){
            return result ;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip) ;
            result = propiedad.hipotecar(this) ;
        }
        if(result){
            diario.ocurreEvento("El jugador " + getNombre() + " hipoteca la propiedad " + ip);
        }
        return result ;
    }
     
     boolean cancelarHipoteca(int ip){
        boolean result = false ;
        if (this.isEncarcelado()){
            return result ;
        }
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = propiedades.get(ip) ;
            float cantidad = propiedad.getImporteCancelarHipoteca() ;
            boolean puedoGastar = this.puedoGastar(cantidad) ;
            if (puedoGastar){
                result = propiedad.cancelarHipoteca(this) ;
                if(result){
                    diario.ocurreEvento("El jugador " + getNombre() + " cancela la hipoteca de la propiedad " + ip);
                }
            }
        }
        return result ;
    }
    
     boolean comprar (TituloPropiedad titulo){
         boolean result = false;
         if (this.isEncarcelado()){
            return result ;
        }
        if (this.getPuedeComprar()){
            float precio = titulo.getPrecioCompra() ;
            if (this.puedoGastar(precio)){
                result = titulo.Comprar(this) ;
                if(result){
                    propiedades.add(titulo) ;
                    diario.ocurreEvento("El jugador " + getNombre() + " compra la propiedad " + titulo.toString());
                }
                puedeComprar = false ;
            }
            
        }
        return result;
     }
     
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
}
