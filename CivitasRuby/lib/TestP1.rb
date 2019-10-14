# encoding: utf-8

require_relative "Dado"
require_relative "TipoSorpresa"
require_relative "TipoCasilla"
require_relative "estados_juego"
require_relative "Tablero"
require_relative "diario"
require_relative "MazoSorpresas"
require_relative "Sorpresa"

module Civitas
  class TestP1
    
    def self.main
      @prueba = Dado.instance
      @diario = Diario.instance
      
      
      #______________________________________________________________________
      puts "Test 1 : ¿Quién empieza?"
      100.times do
        puts @prueba.quienEmpieza(4)
      end   
      puts "_________________________________________\n\n"
      #_______________________________________________________________________
      puts "Test 2 : Probando el dado"
      10.times do
      @prueba.tirar
      test = @prueba.ultimoResultado
      puts test
      @prueba.debug = true
      @prueba.tirar
      test = @prueba.ultimoResultado
      puts test
      @prueba.debug = false
      if (@prueba.salgoDeLaCarcel)
        puts "He sacado un " + @prueba.ultimoResultado.to_s + ". Podría salir de la cárcel\n\n"
      end
      end
      
      puts "_________________________________________\n\n"
      #_______________________________________________________________________
      puts "Test 3 : Elementos de un enumerado"
      puts TipoSorpresa::IRCARCEL
      puts TipoCasilla::CALLE
      puts EstadosJuego::INICIO_TURNO
      puts "_________________________________________\n\n"
      #_______________________________________________________________________
      puts "Test 4 : MazoSorpresas y Diario"
      @mazo = MazoSorpresas.new(true)
      s1 = @mazo.siguiente
      s2 = @mazo.siguiente
      @mazo.alMazo(s1)
      @mazo.alMazo(s2)
      @mazo.siguiente
      @mazo.inhabilitarCartaEspecial(s2)
      @mazo.habilitarCartaEspecial(s2)
      while(@diario.eventos_pendientes)
        puts "#{@diario.leer_evento}"
      end
      puts "_________________________________________\n\n"
      #_______________________________________________________________________
      puts "Test 5 : Tablero"
      @tablero = Tablero.new(2)
      puts (@tablero.nuevaPosicion(@prueba.ultimoResultado, @prueba.tirar)).to_s
      @tablero.aniadeJuez
      @tablero.aniadeCasilla(Casilla.casillaDescanso("Prueba"))
      p @tablero
      10.times do
        puts (@tablero.nuevaPosicion(@prueba.ultimoResultado, @prueba.tirar)).to_s
      end
    end
    
    
    
    
  end
  Civitas::TestP1.main
end
