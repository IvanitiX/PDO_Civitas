#encoding:utf-8
require_relative "OperacionesJuego"
require_relative "Respuestas"
require_relative "GestionesInmobiliarias"
require_relative "SalidasCarcel"
require 'io/console'

module Civitas

  class VistaTextual

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      #STDIN.getch
      gets
       #Con getch me salia un fallo, puse gets
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts "#{tab} #{index.to_s} - #{l}"
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n #{tab} Elige una opción: ",
                          "#{tab} Valor erróneo")
      return opcion
    end
    
    def salirCarcel
      puts "<<-------------Salir Carcel---------------"
      listaRespuestas = [SalidasCarcel::PAGANDO, SalidasCarcel::TIRANDO]
      opcion = menu("¿Cómo quieres intentar salir de la cárcel? (Selecciona un número)",
      listaRespuestas)
      puts "---------------Salir Carcel------------->>"
      return listaRespuestas[opcion]
    end

    
    def comprar
      puts "<<-------------Comprar---------------"
      listaRespuestas = [Respuestas::SI,Respuestas::NO]
      opcion = menu("¿Quieres comprar la calle en la que estás situado? (Selecciona un número)",
      listaRespuestas)
      puts "---------------Comprar------------->>"
      return listaRespuestas[opcion]
      
    end

    def gestionar
      puts "<<-------------Gestionar---------------"
      listaRespuestas = [GestionesInmobiliarias::VENDER, GestionesInmobiliarias::HIPOTECAR, 
        GestionesInmobiliarias::CANCELAR_HIPOTECA, GestionesInmobiliarias::CONSTRUIR_CASA, 
        GestionesInmobiliarias::CONSTRUIR_HOTEL,GestionesInmobiliarias::TERMINAR]
      opcion1 = menu("¿Que gestión inmobiliaria deseas hacer? (Selecciona un número)", 
      listaRespuestas)
      
      propiedades = @juegoModel.getJugadorActual.propiedades
      nombres = Array.new 
      
      for p in propiedades
        nombres << p.to_s
      end
      
      
      if (opcion1 != 5 && @juegoModel.getJugadorActual.propiedades.size != 0)
        opcion2 = menu("¿Que propiedad quiere gestionar? (Selecciona un número)",
        nombres)
        @iGestion = opcion1
        @iPropiedad = opcion2
      else
        @iGestion = 5
      end
      
      if (@juegoModel.getJugadorActual.propiedades.size == 0 && @iGestion != 5)
        puts "[!] No es por nada, pero... ¿no crees que no tiene sentido que puedas gestionar algo que no existe? (Error 404). Fin de turno."
      end
      puts "---------------Gestionar------------->>"
    end

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts "<<-------------Operaciones Juego---------------"
      puts operacion
      puts "---------------Operaciones Juego------------->>"
    end

    def mostrarEventos
      puts "<<-------------Evento Diario---------------"
      while (Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
      puts "---------------Evento Diario------------->>"
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      puts "<<-------------Jugador Actual---------------"
      puts @juegoModel.getJugadorActual.to_s
      puts "---------------Jugador Actual------------->>"
      puts "<<-------------Casilla Actual---------------"
      puts @juegoModel.getCasillaActual.to_s
      puts "---------------Casilla Actual------------->>"
    end

    
  end

end
