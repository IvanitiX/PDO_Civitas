#encoding:utf-8

require_relative "CivitasJuego"
require_relative "OperacionesJuego"
require_relative "OperacionInmobiliaria"
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
        
        if(!@juego.finalDelJuego)
          case op
          when OperacionesJuego::COMPRAR
            if (@vista.comprar == Respuestas::SI)
              @juego.comprar
            end
            @juego.siguientePasoCompletado(op)
          when OperacionesJuego::GESTIONAR
            @vista.gestionar
            iGestion = @vista.getGestion
            iPropiedad = @vista.getPropiedad
            
            listaRespuestas = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, 
        GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, 
        GestionesInmobiliarias::CONSTRUIR_HOTEL,GestionesInmobiliarias::TERMINAR]
            opInmobiliaria = OperacionInmobiliaria.new(listaRespuestas[iGestion], iPropiedad)
            
            case opInmobiliaria.gestion
              when GestionesInmobiliarias::VENDER
                @juego.vender(iPropiedad)
              when GestionesInmobiliarias::HIPOTECAR
                @juego.hipotecar(iPropiedad)
              when GestionesInmobiliarias::CANCELAR_HIPOTECA
                @juego.cancelarHipoteca(iPropiedad)
              when GestionesInmobiliarias::CONSTRUIR_CASA
                @juego.construirCasa(iPropiedad)
              when GestionesInmobiliarias::CONSTRUIR_HOTEL
                @juego.construirHotel(iPropiedad)
              when GestionesInmobiliarias::TERMINAR
                @juego.siguientePasoCompletado(op)
            end
            
          when OperacionesJuego::SALIR_CARCEL
            if (@vista.salirCarcel == SalidasCarcel::PAGANDO)
              @juego.salirCarcelPagando
            else
              @juego.salirCarcelTirando
            end
            @juego.siguientePasoCompletado(op)
            
          end      
        end       
      end  
      @juego.ranking
    end
    
  end
end
