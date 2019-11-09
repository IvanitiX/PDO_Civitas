# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

require "singleton"

module Civitas
  class Dado
    include Singleton
    attr_reader :ultimoResultado
    attr_writer :debug
    @@salidaCarcel = 5
    
    def initialize
      @debug = false
      @ultimoResultado = 0
    end
    
    def tirar
      numerosDado = %w{ 1 2 3 4 5 6 }
      tirada = 3
      if(!@debug)
        tirada = rand(numerosDado.length) + 1
      end
      @ultimoResultado = tirada
      return tirada
    end
    
    def salgoDeLaCarcel
      return tirar == @@salidaCarcel ;
    end
    
    def quienEmpieza(n)
      return rand(0..n-1)
    end
    
  end
end
