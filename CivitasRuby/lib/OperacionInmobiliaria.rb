# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class OperacionInmobiliaria
    def initialize(gest, ip)
      @numPropiedad = ip
      @gestion = gest
    end
    
    attr_reader :gestion, :numPropiedad
  end
end
