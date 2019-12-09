# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaImpuesto < Casilla
    
    def initialize (importe,nombre)
      @importe = importe
      super(nombre)
    end
    
    def to_s
      s = super
      s += "\n Importe del impuesto:  #{@importe} "
      return s
    end

    def recibeJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        super
        todos[actual].pagaImpuesto(importe)
      end
    end
  end
end
