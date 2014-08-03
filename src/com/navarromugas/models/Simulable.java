package com.navarromugas.models;

import java.awt.Graphics;

public interface Simulable {

  public void dibujar(Graphics g);

  public void calcularEstadoSiguiente();
}
