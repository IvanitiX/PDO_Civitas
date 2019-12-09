# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "Jugador"

module Civitas
  
  class Jugador
  end

  
  class JugadorEspeculador < Jugador
    
    @@factorEspeculador = 2
    
    def self.otro(unJugador, fianza)
      a_devolver = super(unJugador)
      a_devolver.setFianza(fianza)
      
      return a_devolver
    end
    
    def to_s
      a_devolver = super
      a_devolver = a_devolver + "\nSOY ESPECULADOR\n"
      
      return a_devolver
    end
    
    def setFianza(fi)
      @fianza = fi
    end
    
    def puedoEdificarCasa(titulo)
      return titulo.numCasas < @@CasasMax*@@factorEspeculador && puedoGastar(titulo.precioEdificar)
    end
    
    def puedoEdificarHotel(titulo)
      return titulo.numHoteles < @@HotelesMax*@@factorEspeculador && puedoGastar(3*titulo.precioEdificar)
    end
    
  end
end
