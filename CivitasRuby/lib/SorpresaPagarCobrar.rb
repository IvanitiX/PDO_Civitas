# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  
  class SorpresaPagarCobrar < Casilla
    
    def initialize(valor, texto)
      super(texto)
      @valor = valor
      
    end
    
    def aplicarAJugador(actual, todos )
      
      super
      
      todos[actual].modificarSaldo(@valor)
      
    end
    
    def to_s
      a_devolver = super
      a_devolver = a_devolver + "Valor: #{@valor}\n"
      return a_devolver
    end
    
  end
  
  
  
  
end
