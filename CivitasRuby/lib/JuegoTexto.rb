# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
# encoding:utf-8
require_relative "CivitasJuego"
require_relative "VistaTextual"
require_relative "Controlador"
module Civitas
  class JuegoTexto
    def self.main
      nombres = Array.new
      nombres << "Ivan"
      nombres << "Mumo"
      
      Dado.instance.debug = true
      
      juego = CivitasJuego.new(nombres)
      vista = VistaTextual.new()
      control = Controlador.new(juego, vista)
      control.juega
    end
  end
  Civitas::JuegoTexto.main
end
