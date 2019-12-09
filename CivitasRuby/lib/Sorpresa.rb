#encoding UTF-8

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

module Civitas
  class Sorpresa
    private_class_method :new
    
    def initialize(texto)
      @texto = texto
    end
    
    def jugadorCorrecto(actual, todos)
        return ( actual >= 0 and actual < todos.size )
    end
    
    def informe (actual, todos)
        if (jugadorCorrecto(actual, todos))
            Diario.instance.ocurre_evento("Se ha aplicado una sorpresa de tipo #{self.class.name.split('::').last} a #{todos[actual].nombre}")
        end
    end
    
    def aplicarAJugador(actual, todos )
      if (jugadorCorrecto(actual, todos))
        informe(actual, todos)
      end
    end
    
    def to_s
      return "---#{self.class.name.split('::').last}--- \n Texto= {#@texto}\n"
    end
    
  end
end
