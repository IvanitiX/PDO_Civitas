# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez
require_relative "Dado"
require_relative "titulo_propiedad"

module Civitas
  class Jugador
    
    @@CasasMax = 4
    @@HotelesMax = 4
    @@CasasPorHotel = 4
    @@PasoPorSalida = 1000.0
    @@SaldoInicial = 7500.0
    @@PrecioLibertad = 200.0
    
    attr_reader :nombre, :numCasillaActual, :saldo, :puedeComprar, :encarcelado, :propiedades
    
    private_class_method :new
    
    def initialize(nombre, encarcelado = false, numCasillaActual = 0, puedeComprar = false, saldo = @@SaldoInicial, salvoconducto = nil, propiedades = Array.new)
      @nombre = nombre
      @encarcelado = encarcelado
      @numCasillaActual = numCasillaActual
      @puedeComprar = puedeComprar
      @saldo = saldo
      @salvoconducto = salvoconducto
      @propiedades = propiedades
      
      @diario = Diario.instance
      @dado = Dado.instance
    end
    
    def self.nuevo(nombre)
      new(nombre)
    end
    
    def self.otro(otro)
      new(otro.nombre, otro.encarcelado, otro.numCasillaActual, otro.puedeComprar, otro.saldo, otro.salvoconducto, otro.propiedades)
    end
    
    def CasasPorHotel
      @@asasPorHotel
    end
    
    def encarcelar(numCasillaCarcel)
      if(debeSerEncarcelado)
        moverACasilla(numCasillaCarcel)
        @encarcelado = true
        @diario.ocurre_evento(" #{@nombre} va a ir a la carcel.")
      end
      return @encarcelado
    end
    
    def debeSerEncarcelado
      carcel = false
      if(tieneSalvoconducto)
        perderSalvoconducto
      end
      
      if(!tieneSalvoconducto and !@encarcelado)
        carcel =true
      end
      return carcel
    end
    
    def moverACasilla(numCasilla)
      if (@encarcelado)
        return false
      else
        @numCasillaActual = numCasilla
        @puedeComprar = false
        @diario.ocurre_evento("El jugador #{@nombre} se ha movido de casilla.")
        return true
      end
    end
    
    def modificarSaldo(cantidad)
      @saldo = @saldo + cantidad
      return true
    end
    
    def tieneSalvoconducto
      return @salvoconducto != nil
    end
    
    def toString
      puts "Jugador{nombre= #{@nombre}, numCasillaActual= #{@numCasillaActual} , saldo= #{@saldo} , puedeComprar= #{@puedeComprar} , encarcelado= #{@encarcelado} , diario= #{@diario} , salvoconducto= #{@salvoconducto} }"
    end
    
    def puedeComprarCasilla
      @puedeComprar = !@encarcelado
      return @puedeComprar
    end
    
    def enBancarrota
      return @saldo <= 0
    end
    
    def tieneAlgoQueGestionar
      return @propiedades!=nil
    end

    def perderSalvoconducto
      @salvoconducto.usada
      @salvoconducto = nil
    end
    
    def obtenerSalvoconducto(sorpresa)
      if (@encarcelado)
        return false
      else
        @salvoconducto = sorpresa
        return true
      end
    end
    
    def puedeSalirCarcelPagando
      return @saldo >= @@PrecioLibertad
    end
    
    def salirCarcelPagando
      if(puedeSalirCarcelPagando)
        paga(@@PrecioLibertad)
        @diario.ocurre_evento("#{@nombre} ha pagado la fianza y sale de la carcel.")
        @encarcelado = false
        return true
      else
        return false
      end
    end
    
    def salirCarcelTirando
      if (@dado.salgoDeLaCarcel)
        @encarcelado = false
        @diario.ocurre_evento("#{@nombre} ha salido de la carcel por el dado.")
      end
      return @encarcelado
    end
    
    def puedoGastar(precio)
      if (@encarcelado)
        return false
      else 
        return @saldo >= precio
      end
    end
    
    def recibe (cantidad)
      if (@encarcelado)
        return false
      else 
        return modificarSaldo(cantidad)
      end
    end
    
    def paga(cantidad)
      return modificarSaldo(-cantidad)
    end
    
    def pagaImpuesto(cantidad)
      if (@encarcelado)
        return false
      else
        return paga(cantidad)
      end
    end
    
    def pagaAlquiler(cantidad)
      if (@encarcelado)
        return false
      else 
        return paga(cantidad)
      end
    end
    
    def pasaPorSalida
      return modificarSaldo(@@PasoPorSalida)
    end
    
    def vender(ip)
      if (!@encarcelado)
        if (existeLaPropiedad(ip))
          if (@propiedades[ip].vender(self))
            @propiedades.delete_at(ip)
            @diario.ocurre_evento("Se ha vendido la propiedad")
            self.to_s
            return true
          end
        end
      end
      return false
    end
    
    def existeLaPropiedad(ip)
      return (ip < @propiedades.size)
    end
    
    def puedoEdificarCasa(titulo)
      return titulo.numCasas < @@CasasMax && puedoGastar(titulo.precioEdificar)
    end
    
    def puedoEdificarHotel(titulo)
      return titulo.numHoteles < @@HotelesMax && puedoGastar(3*titulo.precioEdificar)
    end
    
    def cantidadCasasHoteles
      edificios = 0 
      for i in @propiedades do
        @edificios = @edificios + propiedades[i].cantidadCasasHoteles()
      end
      return edificios
    end
    
    def construirCasa(ip)
      result = false
      if(@encarcelado)
        return result
      end
      if(existeLaPropiedad(ip))
        propiedad = @propiedades[ip]
        puedoEdificarCasa = puedoEdificarCasa(propiedad)
        if(puedoEdificarCasa)
          result = propiedad.construirCasa(self)
          if(result)
            @diario.ocurre_evento("El jugador  #{@nombre}  construye casa en la propiedad #{ip}")
          end
        end
      end
      return result
    end
    
    def construirHotel(ip)
      result = false
      if(@encarcelado)
        return result
      end
      if(existeLaPropiedad(ip))
      propiedad = @propiedades[ip]
        puedoEdificarHotel = puedoEdificarHotel(propiedad)
        if(puedoEdificarHotel)
          result = propiedad.construirHotel(self)
          casasPorHotel = @@CasasPorHotel
          @propiedades[ip].derruirCasas(casasPorHotel, self)
          if(result)
            @diario.ocurre_evento("El jugador  #{@nombre}  construye hotel en la propiedad #{ip}")
          end
        end
      end
      return result
    end
     
    def hipotecar(ip)  
      result = false
      if(@encarcelado)
        return result
      end
      if(existeLaPropiedad(ip))
          propiedad = @propiedades[ip]
          result = propiedad.hipotecar(self)
          if(result)
            @diario.ocurre_evento("El jugador  #{@nombre}  hipoteca la propiedad #{ip}")
          end
        end
        return result
      end

     
    def cancelarHipoteca(ip)
      propiedad = @propiedades[ip]
      cantidad = propiedad.getImporteCancelarHipoteca
      puedoGastar = puedoGastar(cantidad)
      if(puedoGastar)
        result = propiedad.cancelarHipoteca(self)
        if(result)
          @diario.ocurre_evento("El jugador #{@nombre} cancela la hipoteca de la propiedad #{ip}")
        end
      end
      return result
    end
    
    def comprar (titulo)
      result = false
      if(@encarcelado)
        return result
      end
      if(@puedeComprar)
        precio = titulo.precioCompra
        if (puedoGastar(precio))
          result = titulo.comprar(self)
          if(result)
            @propiedades << titulo
            @diario.ocurre_evento("El jugador #{@nombre} compra la propiedad #{titulo.to_s}")
          end
          @puedeComprar = false
        end
      end
      return result
    end

    
    private
    
    def self.CasasMax
      @@CasasMax
    end
    
    def self.HotelesMax
      @@HotelesMax
    end
    
    def self.PasoPorSalida
      @@PasoPorSalida
    end

    def self.PrecioLibertad
      @@PrecioLibertad
    end
    
    def <=> (otro)
      otro.saldo <=> saldo
    end
    
   def to_s
        return "Jugador{ nombre= #{@nombre} \n numCasillaActual= #{@numCasillaActual} \n saldo= #{@saldo} \n puedeComprar= #{@puedeComprar}\n encarcelado= #{@encarcelado}\n diario= #{@diario}\n salvoconducto= #{@salvoconducto}\n propiedades = #{@propiedades} }";
   end
   
   public :to_s
  end
end
