# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  
  class SorpresaCarcel < Sorpresa
    public_class_method :new
    
    def initialize(tablero)
      texto = "Te han pillado dando galletas Oreo rellenas de pasta de dientes."
      super(texto)
      @tablero = tablero
      
    end
    
    def aplicarAJugador(actual, todos )
      
      super
      
      todos[actual].encarcelar( @tablero.numCasillaCarcel )
      
    end
    
    
    
  end
  
  
  
  
end
