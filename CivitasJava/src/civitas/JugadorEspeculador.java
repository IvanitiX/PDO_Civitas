/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author ivan
 */
public class JugadorEspeculador extends Jugador {
    
    private float fianza ;
    private static int FactorEspeculador = 2 ;
    
    public JugadorEspeculador(Jugador convertido, float fianza){
        super(convertido) ;
        this.fianza = fianza ;
        for (TituloPropiedad titulo : convertido.getPropiedades()){
            titulo.actualizarPropietarioPorConversion(this);
        }
    }
    
    protected static int getCasasMax(){
        return FactorEspeculador*CasasMax ;
    }
    
    protected static int getHotelesMax(){
        return FactorEspeculador*HotelesMax ;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
       return super.pagaImpuesto(cantidad/2) ;
    }
    
    @Override
    public boolean isEspeculador(){
        return true ;
    }
    
    @Override
    boolean puedoEdificarCasa(TituloPropiedad titulo){
         return titulo.getNumCasas() < this.getCasasMax() && puedoGastar(titulo.getPrecioEdificar()) ;
    }
    
    @Override
    boolean puedoEdificarHotel(TituloPropiedad titulo){
         return titulo.getNumHoteles() < this.getHotelesMax() && puedoGastar(titulo.getPrecioEdificar()) && titulo.getNumCasas() >= getCasasPorHotel() ;
    }
    
    
    @Override
    protected boolean debeSerEncarcelado(){
        boolean debeSerEncarceladoClasico = super.debeSerEncarcelado() ;
        boolean puedoPagarFianza = super.puedoGastar(fianza);
        return debeSerEncarceladoClasico && !puedoPagarFianza ;
    }
    
    @Override
    public String toString(){
        String info = super.toString() ;
        info += "\n ¡Este jugador es un Especulador!" + "\n Fianza : " + fianza ;
        return info ;
    }
    
}
