# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative "CivitasJuego"
require_relative "OperacionesJuego"
module Civitas
  
  class Controlador
    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end
    
    def juega()
      @vista.setCivitasJuego(@juego)
      
      while(!@juego.finalDelJuego)
        @vista.actualizarVista
        @vista.pausa
        op = @juego.siguientePaso
        @vista.mostrarSiguienteOperacion(op)
        
        if(op != OperacionesJuego::PASAR_TURNO)
          @vista.mostrarEventos
        end
      end
      
      @juego.ranking
    end
    
  end
end
