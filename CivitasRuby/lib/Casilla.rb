#encoding:utf-8

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

module Civitas
  class Casilla
    attr_reader :nombre
        
    def initialize(nombre)
      @nombre = nombre
      @diario = Diario.instance
    end
 
    def informe (actual, todos)
      if (jugadorCorrecto(actual, todos))
        @diario.ocurre_evento(todos[actual].nombre + "ha llegado a la casilla " + to_s )
      end
    end
    
    def jugadorCorrecto(actual, todos)
      return ( actual >= 0 && actual < todos.size )
    end
    
    def to_s
      s = "---#{self.class.name.split('::').last}--- \n Nombre:  #{@nombre}"
      return s
    end   
    
    def recibeJugador(actual,todos)
      informe(actual, todos);
    end
 
  end
end
