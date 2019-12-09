# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

# To change this template file, choose Tools | Templates
# and open the template in the editor.

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

require_relative "CivitasJuego"
require_relative "Dado"
require_relative "estados_juego"
require_relative "Tablero"
require_relative "diario"
require_relative "MazoSorpresas"
require_relative "Sorpresa"

module Civitas
  class TestP4
    def self.main
      
      @jugador = Jugador.nuevo("Mumo")
      
      titulo = TituloPropiedad.new("Calderon de la Barca", 200, 50, 0.1, 300, 100)
      
      @jugador.propiedades << titulo
      
      puts "JUGADOR ANTES DE CONVERTIRSE\n"
      
      puts @jugador.to_s
      
      @especulador = Jugador.nuevoEspeculador(@jugador, 1000)
      
      puts "\nJUGADOR DESPUES DE CONVERTIRSE\n"
      
      puts @especulador.to_s      
      
    end
  end
  Civitas::TestP4.main
end

