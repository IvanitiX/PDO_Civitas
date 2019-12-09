# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  
  class SorpresaJugador < Sorpresa
    public_class_method :new
    
    def initialize(valor, texto)
      
      super(texto)
      @valor = valor

    end
    
    def aplicarAJugador_porJugador (actual, todos )
      
      s = Sorpresa::sorpresaResto(TipoSorpresa::PAGARCOBRAR, -@valor, "Se dan #{@valor} al jugador que la use")
      j = Sorpresa::sorpresaResto(TipoSorpresa::PAGARCOBRAR, (todos.size - 1)*@valor, "Se recibe #{(todos.size - 1)*@valor} del resto de jugadores")
        
      for i in (0..todos.size) do
        if (i != actual)
          s.aplicarAJugador(i, todos)
        else
          j.aplicarAJugador(i, todos)
        end
      end
      
    end
    
    def to_s
      a_devolver = super
      a_devolver = a_devolver + "Valor: #{@valor}\n"
      return a_devolver
    end
    
  end

  
end