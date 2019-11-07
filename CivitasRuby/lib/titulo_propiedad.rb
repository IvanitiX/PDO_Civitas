# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

class TituloPropiedad
  @@factorInteresesHipoteca = 1.1
  
  attr_reader :nombre, :numCasas, :numHoteles,
    :precioCompra, :precioEdificar, :propietario, :hipotecado
  
  def initialize(nombre,alquilerBase,factorRevalorizacion, 
            hipotecaBase,precioCompra,precioEdificar)
          
    @nombre = nombre
    @alquilerBase = alquilerBase
    @factorRevalorizacion = factorRevalorizacion
    @hipotecaBase = hipotecaBase
    @precioCompra = precioCompra
    @precioEdificar = precioEdificar
    @propietario = nil
    @hipotecado = false
    @numCasas = 0
    @numHoteles = 0
  end
  
  def getImporteCancelarHipoteca
    return getImporteHipoteca() * factorInteresesHipoteca ;
  end
  
  def cancelarHipoteca(jugador)
    hecho = false
    if(esEsteElPropietario(jugador) and @hipotecado)
      if (jugador.paga(getImporteCancelarHipoteca))
        @hipotecado = false
        hecho = true
      end
    end
    return hecho
  end
  
  def hipotecar(jugador)
    hecho = false
    if(!@hipotecado and esEsteElPropietario(jugador))
      jugador.recibe(getImporteHipoteca)
      @hipotecado = true 
      hecho = true 
    end
    return hecho 
  end
  
  def tramitarAlquiler(jugador)
    if(tienePropietario and !esEsteElPropietario(jugador))
      jugador.pagaAlquiler(getPrecioAlquiler)
      @propietario.recibe(getPrecioAlquiler)
    end
  end
  
  def cantidadCasasHoteles
    return @numCasas + @numHoteles
  end
  
  def derruirCasas(n, jugador)
    hecho = false
    if(esEsteElPropietario(jugador) and @numCasas >= n)
      @numCasas = @numCasas - n
      hecho = true
    end
    return hecho
  end
  
  def construirCasa(jugador)
    construida = false
    if (esEsteElPropietario(jugador))
      if(jugador.paga(precioEdificar))
        @numCasas = @numCasas + 1
        construida = true
      end
    end
    return construida
  end

  def construirHotel(jugador)
    construido = false
    if (esEsteElPropietario(jugador))
      if(jugador.paga(3*@precioEdificar))
        @numHoteles = @numHoteles + 1
        construido = true
      end
    end
    return construido
  end

  def Comprar(jugador)
    comprado = false
    if (!tienePropietario)
      if(jugador.paga(@precioCompra))
        comprado = true
      end
    end
    return comprado
  end
  
  def tienePropietario
    return @propietario != nil
  end

  #def actualizarPropietarioPorConversion(jugador)
  
  def vender(jugador)
    jugador.modificarSaldo(getprecioVenta())
    derruirCasas(@numCasas,jugador)
    @numHoteles = 0
    return false
  end

  private
  
  def getPrecioAlquiler
    alquiler = 0
    if (!@hipotecado and !propietarioEncarcelado)
      alquiler = @alquilerBase *(1+(@numCasas* 0.5)+(@numHoteles*2.5))
    end
    return alquiler
  end
  
  def propietarioEncarcelado
    if (tienePropietario)
      return @propietario.encarcelado
    else
      return false
    end
  end
  
  def getPrecioVenta
    precioVenta = @precioCompra+(@numCasas+5*@numHoteles)*@precioEdificar*@factorRevalorizacion
    return precioVenta
  end
  
  def esEsteElPropietario(jugador)
    return @propietario == jugador
  end
  
  def getImporteHipoteca
    return (@hipotecaBase*(1+(@numCasas*0.5)+(@numHoteles*2.5)))
  end
  
  def vender(jugador)
    
    if (esEsteElPropietario(jugador) and !@hipotecado )
      jugador.recibe(@precioVenta)
      
      derruirCasas(@numCasas, jugador)
      @numHoteles = 0
      @propietario = nil
      return true
    end
    return false
  end
  
  def to_s
        return "TituloPropiedad{ \n nombre= #{@nombre} , alquilerBase= #{@alquilerBase}, factorRevalorizacion= #{@factorRevalorizacion} , hipotecaBase= #{@hipotecaBase} , hipotecado= #{@hipotecado} , numCasas= #{@numCasas} , numHoteles= #{@numHoteles} , precioCompra= #{@precioCompra} , precioEdificar= #{@precioEdificar} , propietario= #{@propietario} }"
  end
  
  public :to_s
end
