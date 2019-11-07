#encoding:utf-8
require_relative 'OperacionesJuego'
require 'io/console'

module Civitas

  class Vista_textual

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
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
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end
    
    def salirCarcel
      listaRespuestas = [SalirCarcel::PAGANDO, SalirCarcel::TIRANDO]
      opcion = menu("¿Cómo quieres intentar salir de la cárcel? (Selecciona un número)",
      listaRespuestas)
      return listaRespuestas[opcion]
    end

    
    def comprar
      listaRespuestas = [Respuestas::SI,Respuestas::NO]
      opcion = menu("¿Quieres comprar la calle en la que estás situado? (Selecciona un número)",
      listaRespuestas)
      return listaRespuestas[opcion]
      
    end

    def gestionar
      listaRespuestas = [OperacionesJuego::VENDER, OperacionesJuego::HIPOTECAR, 
        OperacionesJuego::CANCELAR_HIPOTECA, OperacionesJuego::CONSTRUIR_CASA, 
        OperacionesJuego::CONSTRUIR_HOTEL,OperacionesJuego::TERMINAR]
      opcion = menu("¿Que gestión inmobiliaria deseas hacer? (Selecciona un número)", 
      listaRespuestas)
      @iGestion = opcion
      @iPropiedad = ip
    end

    def getGestion
      return @iGestion
    end

    def getPropiedad
      return @iPropiedad
    end

    def mostrarSiguienteOperacion(operacion)
      puts operacion
    end

    def mostrarEventos
      while (Diario.instance.eventos_pendientes)
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         self.actualizarVista
    end

    def actualizarVista
      puts "==========================================="
      puts @juegoModel.getJugadorActual.to_s
      puts @juegoModel.getCasillaActual.to_s
      puts "==========================================="
    end

    
  end

end
