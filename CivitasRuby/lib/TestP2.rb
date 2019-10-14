# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative "CivitasJuego"
require_relative "Dado"
require_relative "TipoSorpresa"
require_relative "TipoCasilla"
require_relative "estados_juego"
require_relative "Tablero"
require_relative "diario"
require_relative "MazoSorpresas"
require_relative "Sorpresa"

module Civitas
  class TestP2
    def self.main
      
      @jugadores = Array.new
      @jugadores << "Iván"
      @jugadores << "Mumo"
      
      @juego = CivitasJuego.new(@jugadores)
      
      @juego.actualizarInfo
      
      for i in 0..5 do
        puts @juego.mazo.siguiente.to_s
      end
      
      puts @juego.tablero.getCasilla(0).to_s
      puts @juego.tablero.getCasilla(15).to_s
      puts @juego.tablero.getCasilla(4).to_s
      puts @juego.tablero.getCasilla(17).to_s

      
      @juego.actualizarInfo
      @juego.pasarTurno
      @juego.actualizarInfo
      
      @juego.getJugadorActual.modificarSaldo(-10000)
      @juego.actualizarInfo
      
      
    end
  end
  Civitas::TestP2.main
end
