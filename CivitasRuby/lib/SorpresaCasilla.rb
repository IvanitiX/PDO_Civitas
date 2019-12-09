# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
module Civitas
  
  class SorpresaCasilla < Sorpresa
    public_class_method :new
    def initialize(tablero, valor, texto)
      super(texto)
      @tablero = tablero
      @valor = valor
      
    end
    
    def aplicarAJugador(actual, todos )
      
      super
                
      numCasilla = todos[actual].numCasillaActual
        
      numTirada = @tablero.calcularTirada(numCasilla, @valor)
        
      nuevaPosicion = @tablero.nuevaPosicion(numCasilla, numTirada)
        
      todos[actual].moverACasilla(nuevaPosicion)
        
      numCasilla = todos[actual].numCasillaActual
        
      casilla = @tablero.getCasilla(numCasilla)
        
      casilla.recibeJugador(actual,todos)
    end
    
    def to_s
      a_devolver = super
      a_devolver = a_devolver + "Valor: #{@valor}\n"
      return a_devolver
    end
    
  end
  
  
  
  
end