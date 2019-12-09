# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "Sorpresa"


module Civitas
  class CasillaSorpresa < Casilla
    
    def initialize (mazo,nombre)
      @mazo = mazo
      @sorpresa = mazo.siguiente
      super(nombre)
    end
    
    def to_s
      s = super
      s += "\n La sorpresa que saldra es : #{@sorpresa.to_s}"
      return s
    end
    
    def recibeJugador(actual,todos)
      if(self.jugadorCorrecto(actual,todos))
        @sorpresa = @mazo.siguiente() 
        super
        @sorpresa.aplicarAJugador(actual, todos)
      end
    end
  end
end