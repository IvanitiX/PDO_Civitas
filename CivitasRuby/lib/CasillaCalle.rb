# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "titulo_propiedad"

module Civitas
  class CasillaCalle < Casilla
    
    attr_reader :titulo
    
    def initialize (titulo)
      @titulo = titulo
      super(@titulo.nombre)
    end
    
    def to_s
      s = super
      s += "\n #{@titulo.to_s}"
      return s
    end
    
    def recibeJugador(actual,todos)
      if(self.jugadorCorrecto(actual,todos))
        super
        jugador = todos.at(actual)
        if(!@titulo.tienePropietario())
          jugador.puedeComprarCasilla() 
        else
          @titulo.tramitarAlquiler(jugador)
        end
      end
    end

  end
end
