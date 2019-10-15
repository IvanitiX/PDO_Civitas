# encoding:utf-8

#Author: Miguel Muñoz Molina
#Author: Iván Valero Rodríguez

require_relative 'diario'
require_relative "Sorpresa"
module Civitas
  class MazoSorpresas 
    def init
      @sorpresas = Array.new
      @cartasEspeciales = Array.new 
      @barajada = false
      @usadas = 0
      @diario = Diario.instance
      @debug
      @ultimaSorpresa
    end
    
    def initialize(modo = false)
      init
      @debug = modo
      if(@debug)
        @diario.ocurre_evento("Modo debug para el mazo: Activado")
      end
    end
    
    def alMazo(s)
      if(!@barajada)
        @sorpresas << s
      end
    end
    
    def siguiente
      if (!@barajada || @usadas == @sorpresas.size)
        @sorpresas.shuffle
        @usadas = 0
        @barajada = true
      end
      @usadas = @usadas + 1
      @ultimaSorpresa = @sorpresas.at(0)
      @sorpresas.delete_at(0)
      @sorpresas << @ultimaSorpresa
      return @ultimaSorpresa
    end
    
    def inhabilitarCartaEspecial(sorpresa)
      if(@sorpresas.include?(sorpresa))
        @sorpresas.delete_at(@sorpresas.index(sorpresa))
        @cartasEspeciales << sorpresa
        @diario.ocurre_evento("Se ha inhabilitado la sorpresa #{sorpresa.to_s}")
      end
    end
    
    def habilitarCartaEspecial(sorpresa)
      if(@cartasEspeciales.include?(sorpresa))
        @cartasEspeciales.delete_at(@sorpresas.index(sorpresa).to_i)
        @sorpresas << sorpresa
        @diario.ocurre_evento("Se ha habilitado la sorpresa #{sorpresa.to_s}")
      end
    end
    
    private :init
    
  end
end
