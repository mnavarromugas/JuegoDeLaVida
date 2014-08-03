package com.navarromugas.models;

import java.awt.*;
import java.util.ArrayList;

public class Enjambre implements Simulable {

  private ArrayList<Mosca> moscas;
  private static final int N = 10;

  public Enjambre() {
    moscas = new ArrayList<>(N);
    for (int i = 0; i < N; i++) {
      moscas.add(new Mosca(new Color((int) (Math.random()*256), (int) (Math.random()*256), (int) (Math.random()*256))));
    }
  }

  @Override
  public void dibujar(Graphics g) {
    for (Mosca m : moscas)
      m.dibujar(g);
  }

  @Override
  public void calcularEstadoSiguiente() {
    for (Mosca m : moscas)
      m.calcularEstadoSiguiente();
  }
}
