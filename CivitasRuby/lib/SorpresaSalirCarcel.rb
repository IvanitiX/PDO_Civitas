# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "MazoSorpresas"
module Civitas
  
  class SorpresaSalirCarcel < Sorpresa
    public_class_method :new
    
    def initialize(mazo)
      
      texto = "Dejas un cheque al juez en una cuenta sin fondos y se lo ha creido."
      super(texto)
      @mazo = mazo
      
    end
    
    def aplicarAJugador(actual, todos )
      
      alguien = false
      for j in todos do
        if (j.tieneSalvoconducto)
           alguien = true
        end
      end
      
      if (!alguien)
        todos[actual].obtenerSalvoconducto(self)
        salirDelMazo()
      end
      
    end
    
    def salirDelMazo
      @mazo.inhabilitarCartaEspecial(self)
    end
    
    def usada
      @mazo.habilitarCartaEspecial(self)
    end
    
  end
  
  
  
  
end