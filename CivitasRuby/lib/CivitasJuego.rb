# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# encoding:utf-8

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

require_relative "gestor_estados"
require_relative "titulo_propiedad"
require_relative "Jugador"
module Civitas
  
  class CivitasJuego
    
    @@CASILLA_CARCEL = 15
    
    attr_reader :tablero,:mazo
    
    def initialize(nombres)
      @jugadores = Array.new
      
      for i in nombres do
        puts "#{i}"
        @jugadores << Jugador.nuevo(i)
      end
      
      @dado = Dado.instance
      
      @gestorEstados = Gestor_estados.new
      @estado = @gestorEstados.estado_inicial
      @indiceJugadorActual = @dado.quienEmpieza(@jugadores.size)
      @tablero = Tablero.new(@@CASILLA_CARCEL)
      @mazo = MazoSorpresas.new   
      inicializarMazo(@tablero)
      inicializarTablero(@mazo)
      
    end
    
    def finalDelJuego
      gameOver = false
      for j in @jugadores do
        if(j.enBancarrota)
          gameOver = true
        end
      end
      return gameOver
    end
    
    def vender(ip)
      return @jugadores[@indiceJugadorActual].vender(ip)
    end
    
    def construirCasa(ip)
      return @jugadores[@indiceJugadorActual].construirCasa(ip)
    end
    
    def construirHotel(ip)
      return @jugadores[@indiceJugadorActual].construirHotel(ip)
    end
    
    def hipotecar(ip)
      return @jugadores[@indiceJugadorActual].hipotecar(ip)
    end
    
    def cancelarHipoteca(ip)
      return @jugadores[@indiceJugadorActual].cancelarHipoteca(ip)
    end
    
    def salirCarcelPagando
      return @jugadores[@indiceJugadorActual].salirCarcelPagando
    end
    
    def salirCarcelTirando
      return @jugadores[@indiceJugadorActual].salirCarcelTirando
    end
    
    def ranking
      @jugadores = @jugadores.sort
    end
    
    def getCasillaActual
      return @tablero.getCasilla(@jugadores[@indiceJugadorActual].numCasillaActual)
    end
    
    def getJugadorActual
      return @jugadores[@indiceJugadorActual]
    end
    
    def infoJugadorTexto
      return getJugadorActual.to_s
    end
    
    def siguientePasoCompletado(operacion)
      @estado = @gestorEstados.siguienteEstado(getJugadorActual, @estado, operacion)
    end
    
    def actualizarInfo
      puts infoJugadorTexto
      if(getJugadorActual.enBancarrota)
        puts(ranking.to_s)
      end
    end
    
    private
    
    def inicializarTablero(mazo)
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Calderon de la Barca", 200, 50, 0.1, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Montilla", 300, 60, 0.2, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaSorpresa(mazo,"Sorpresa"))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Platon", 400, 50, 0.0, 300, 100)))
        @tablero.aniadeJuez
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Ntra.Sra de Tiscar", 200, 70, 0.1, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaImpuesto(200, "Zona azul"))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Jose de Mora", 300, 50, -0.1, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Baza", 400, 60, 0.2, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaDescanso("Bar La Posada"))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Juan de Echevarria", 200, 50, 0.2, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaSorpresa(mazo,"Sorpresa"))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("San Leon", 300, 70, 0.1, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Via Lactea", 400, 80, -0.2, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Sierra Morena", 200, 80, 0.1, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaSorpresa(mazo,"Sorpresa"))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Casillas", 300, 50, 0.2, 300, 100)))
        @tablero.aniadeCasilla(Casilla.casillaTitulo(TituloPropiedad.new("Francisco Pradilla", 400, 60, 0.0, 300, 100)))
    end
    
    def inicializarMazo(tablero)
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PAGARCOBRAR, 500, "El Gobierno te ha dado una subvencion por tus propiedades."))
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PAGARCOBRAR, -500, "Pagas penalizacion por pasarte de potencia electrica."))
        @mazo.alMazo(Sorpresa.sopresaCasilla(TipoSorpresa::IRCASILLA, tablero, 0, "Ve a la salida y cobra antes que nadie (o no)."))
        @mazo.alMazo(Sorpresa.sopresaCarcel(TipoSorpresa::IRCARCEL, tablero))
        @mazo.alMazo(Sorpresa.sopresaCasilla(TipoSorpresa::IRCASILLA, tablero, 10, "Despues de salir con el runner, ve a La Posada y descansa."))
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PORCASAHOTEL, 100, "El Gobierno te extiende una transferencia para mejorar tus edificios."))
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PORCASAHOTEL, 100, "Hacienda te pide bajo pena de prision que pagues tus tributos."))
        @mazo.alMazo(Sorpresa.sorpresaSalirCarcel(TipoSorpresa::SALIRCARCEL, @mazo))
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PORJUGADOR, 100, "Es tu santo Como no te han comprado nada, te daran dinero."))
        @mazo.alMazo(Sorpresa.sorpresaResto(TipoSorpresa::PORJUGADOR, 100, "Le has roto el coche a los 3  y debes compensarles."))
    end
    
    def pasarTurno
      indiceJugadorActual = (@indiceJugadorActual+1)%@jugadores.size
    end
    
    def contabilizarPasosPorSalida(jugadorActual)
      while(@tablero.getPorSalida > 0)
        jugadorActual.pasaPorSalida
      end
    end
    
  end
  
end
