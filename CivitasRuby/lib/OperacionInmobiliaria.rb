#encoding:utf-8

module Civitas
  class OperacionInmobiliaria
    def initialize(gest, ip)
      @numPropiedad = ip
      @gestion = gest
    end
    
    attr_reader :gestion, :numPropiedad
  end
end
