#encoding UTF-8

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

module Civitas
  class Sorpresa

    private_class_method :new
    
    def initialize(tipo, mazo, tablero, valor, texto)
      init
      @tipo = tipo
      @mazo = mazo
      @tablero = tablero
      @valor = valor
      @texto = texto
      @diario = Diario.instance
    end

    def self.sorpresaCarcel(tipo, tablero)
      texto = "Te han pillado dando galletas Oreo rellenas de pasta de dientes."
      new(tipo, nil, tablero, -1, texto )
    end
    
    def self.sorpresaCasilla(tipo, tablero, valor, texto)
      new(tipo, nil, tablero, valor, texto)
    end
    
    def self.sorpresaSalirCarcel(tipo, mazo)
      texto = "Dejas un cheque al juez en una cuenta sin fondos y se lo ha creido."
      new(tipo, mazo, nil, -1, texto)
    end
    
    def self.sorpresaResto(tipo, valor, texto)
      new(tipo, nil, nil, valor, texto)
    end
    
    def jugadorCorrecto(actual, todos)
        return ( actual >= 0 and actual < todos.size )
    end
    
    def informe (actual, todos)
        if (jugadorCorrecto(actual, todos))
            @diario.ocurre_evento("Se ha aplicado una sorpresa de tipo #{@tipo} a #{todos[actual].nombre}")
        end
    end
    
    def aplicarAJugador(actual, todos )
        if (jugadorCorrecto(actual, todos))
            informe(actual, todos)

            if (@tipo == TipoSorpresa::IRCARCEL)
              aplicarAJugador_irCarcel(actual,todos)
            elsif (@tipo == TipoSorpresa::IRCASILLA)
              aplicarAJugador_irACasilla(actual,todos)
            elsif (@tipo == TipoSorpresa::PAGARCOBRAR)
              aplicarAJugador_pagarCobrar(actual,todos)
            elsif (@tipo == TipoSorpresa::SALIRCARCEL)
              aplicarAJugador_salirCarcel(actual,todos)
            elsif (@tipo == TipoSorpresa::PORCASAHOTEL)
               aplicarAJugador_porCasaHotel(actual,todos)
            elsif (@tipo == TipoSorpresa::PORJUGADOR)
               aplicarAJugador_porJugador(actual,todos)
            end
        end
    end
            
    def salirDelMazo
      if (@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitarCartaEspecial(self)
      end
    end

    def usada
      if(@tipo == TipoSorpresa::SALIRCARCEL)
        @mazo.habilitarCartaEspecial(self)
      end
    end
    
    def to_s
      puts "Sorpresa{ texto= {#@texto}, valor= {#@valor}, tipo= {#@tipo}, mazo= {#@mazo} , tablero= {#@tablero}, diario= {#@diario} }"
    end
    
    private
    
    def init
      @valor = -1
      @tablero = nil
      @mazo = nil
    end
    
    def aplicarAJugador_irCarcel (actual, todos )
        
        todos[actual].encarcelar( @tablero.numCasillaCarcel )
    end
    
    def aplicarAJugador_irACasilla (actual, todos )
                
        numCasilla = todos[actual].numCasillaActual
        
        numTirada = @tablero.calcularTirada(numCasilla, @valor)
        
        nuevaPosicion = @tablero.nuevaPosicion(numCasilla, numTirada);
        
        todos[actual].moverACasilla(nuevaPosicion)
        
        numCasilla = todos[actual].numCasillaActual
        
        casilla = @tablero.getCasilla(numCasilla);
        
        casilla.recibeJugador(actual,todos);
    end
    
    def aplicarAJugador_pagarCobrar (actual, todos )           
        todos[actual].modificarSaldo(@valor)
    end
    
    def aplicarAJugador_salirCarcel (actual, todos )
        alguien = false
        for j in todos do
          if (j.tieneSalvoconducto)
             alguien = true
          end
        end
        
        if (!alguien)
          todos[actual].obtenerSalvoconducto(Sorpresa::sorpresaSalirCarcel(TipoSorpresa::SALIRCARCEL, @mazo))
          salirDelMazo()
        end
    end
    
    def aplicarAJugador_porCasaHotel (actual, todos )
      jugador = todos[actual]
      jugador.modificarSaldo ( @valor * jugador.cantidadCasasHoteles() )
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

    
  end
end
