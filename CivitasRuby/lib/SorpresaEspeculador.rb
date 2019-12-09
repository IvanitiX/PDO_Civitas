# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "titulo_propiedad"

module Civitas
  
  class SorpresaEspeculador < Sorpresa
    public_class_method :new
    def initialize(valor, texto)
      super(texto)
      @valor = valor
      
    end
    
    def aplicarAJugador(actual, todos )
      
      super      
      especulador = Jugador.nuevoEspeculador(todos[actual], @valor)
      
      for titulo in todos[actual].propiedades
        titulo.propietario = especulador
      end
      
      todos[actual] = especulador
      
      return especulador
      
    end
    
    def to_s
      a_devolver = super
      a_devolver = a_devolver + "Valor: #{@valor}\n"
      return a_devolver
    end
    
  end
  
  
  
  
end
