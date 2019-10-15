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
    private Sorpresa salvoconducto ;
    private ArrayList<TituloPropiedad> propiedades ;
    private Dado dado = Dado.getInstance() ;

    Jugador(String nombre) {
        this.nombre = nombre;
        this.encarcelado = false;
        this.numCasillaActual = 0 ;
        this.puedeComprar = false ;
        this.saldo = SaldoInicial ;
        salvoconducto = null;
        propiedades = new ArrayList<TituloPropiedad>() ;
    }

    protected Jugador(Jugador otro) {
        this.nombre = otro.nombre;
        this.numCasillaActual = otro.numCasillaActual;
        this.saldo = otro.saldo;
        this.puedeComprar = otro.puedeComprar;
        this.encarcelado = otro.encarcelado ;
    }

    private static int getCasasMax() {
        return CasasMax;
    }

    private static int getHotelesMax() {
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

    protected String getNombre() {
        return nombre;
    }

    int getNumCasillaActual() {
        return numCasillaActual;
    }

    protected float getSaldo() {
        return saldo;
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
    
    boolean obtenerSalvoconducto(Sorpresa sorpresa){
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
            diario.ocurreEvento(nombre + "ha pagado la fianza y sale de la cárcel.");
            return true ;
        }
        else return false ;
    }
    
    boolean salirCarcelTirando(){
        if (dado.salgoDeLaCarcel()){
            encarcelado = true ;
            diario.ocurreEvento(nombre + "ha salido de la cárcel por el dado.");
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
                return true ;
            }
            else return false;
        }
    }
    
    boolean existeLaPropiedad(int ip){
        return (ip < propiedades.size()) ;
    }
    
    boolean puedoEdificarCasa(TituloPropiedad titulo){
        return titulo.getNumCasas() < CasasMax && puedoGastar(titulo.getPrecioEdificar()) ;
    }
    
    boolean puedoEdificarHotel(TituloPropiedad titulo){
        return titulo.getNumHoteles() < HotelesMax && puedoGastar(3*titulo.getPrecioEdificar()) ;
    }
    
    int cantidadCasasHoteles(){
        int edificios = 0 ;
        for(int i = 0 ;i < propiedades.size(); i++){
            edificios += propiedades.get(i).cantidadCasasHoteles() ;
        }
        return edificios ;
    }
    
     boolean construirCasa(int ip){
        throw new UnsupportedOperationException("Sin implemetar") ;
    }
    
     boolean construirHotel(int ip){
        throw new UnsupportedOperationException("Sin implemetar") ;
    }
     
     boolean hipotecar(int ip){
        throw new UnsupportedOperationException("Sin implemetar") ;
    }
     
     boolean cancelarHipoteca(int ip){
        throw new UnsupportedOperationException("Sin implemetar") ;
    }
    
    
}