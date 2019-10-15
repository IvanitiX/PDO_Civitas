# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

require_relative "titulo_propiedad"
require_relative "Sorpresa"

module Civitas
  class Casilla
    attr_reader :nombre, :tipo, :importe, :titulo, :carcel, :mazo, :sorpresa
    
    private_class_method :new
    
    def initialize(titulo, tipo, carcel, mazo, sorpresa, nombre, importe)
      @titulo = titulo
      @nombre = nombre
      @importe = importe
      @tipo = tipo
      @carcel = carcel
      @mazo = mazo
      @sorpresa = sorpresa
      
      @diario = Diario.instance
    end
    
    def self.casillaTitulo(titulo)
      new(titulo, TipoCasilla::CALLE, 0, nil, nil, "", 0.0)
    end
    
    def self.casillaJuez(carcel, nombre)
      new(nil, TipoCasilla::JUEZ, carcel, nil, nil, nombre, 0.0)
    end
    
    def self.casillaSorpresa(mazo, nombre)
      new(nil, TipoCasilla::SORPRESA, 0, mazo, mazo.siguiente, nombre, 0.0)
    end
    
    def self.casillaImpuesto(importe, nombre)
      new(nil, TipoCasilla::IMPUESTO, 0, nil, nil, nombre, importe)
    end
    
    def self.casillaDescanso(nombre)
      new(nil, TipoCasilla::DESCANSO, 0, nil, nil, nombre, 0.0)
    end
    
    
    def informe (actual, todos)
      if (jugadorCorrecto(actual, todos))
        @diario.ocurreEvento(todos[actual].nombre + "ha llegado a la casilla " + to_s )
      end
    end
    
    def jugadorCorrecto(actual, todos)
      return ( actual >= 0 && actual < todos.size )
    end
    
    def to_s
      s = "Casilla \n Nombre:  #{@nombre} \n Tipo: #{@tipo}"
        if(@tipo==TipoCasilla::CALLE)
          s = "#{s} \n #{@titulo.to_s}"
        elsif(@tipo==TipoCasilla::IMPUESTO)
          s ="#{s} \n Importe del impuesto:  #{@importe} "
        elsif(@tipo==TipoCasilla::JUEZ)
          s ="#{s} \n El jugador sera enviado a la carcel"
        elsif(@tipo==TipoCasilla::SORPRESA)
          s ="#{s} \n La sorpresa que saldra es :  #{@sorpresa.to_s}"
        end
        return s
    end
    
    def recibeJugador_impuesto(actual, todos)
      if(jugadorCorrecto(actual,todos))
        todos[actual].pagaImpuesto(importe)
      end
    end
    
    def recibeJugador_juez(actual, todos)
      if(jugadorCorrecto(actual,todos))
        todos[actual].encarcelar(@carcel)
      end
    end
    
    private
    
    def getTituloPropiedad
      if (@tipo == TipoCasilla::CALLE)
        return @titulo
      else
        return nil
      end
    end

      public :to_s
  end
end