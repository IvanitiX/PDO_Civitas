# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class CasillaJuez < Casilla
    
    def initialize (carcel, nombre)
      @carcel = carcel
      super(nombre)
    end
    
    def to_s
      s = super
      s += "\n El jugador sera enviado a la carcel"
      return s
    end
    
    def recibeJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        super
        todos[actual].encarcelar(@carcel)
      end
    end

  end
end