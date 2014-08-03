package com.navarromugas.models;

import java.awt.*;

public class Mosca {
  
  public static final int MAX_LONGITUD_DEL_PASO = 10;
  public static final int MAX_COORDENADA_Y_DE_UBICACION = 640;
  public static final int MAX_COORDENADA_X_DE_UBICACION = 480;
  
  private Color color;
  private int x;
  private int y;
  private int pasos;

  public Mosca(Color color) {
    y = (int) Math.random() * MAX_COORDENADA_X_DE_UBICACION;
    x = (int) Math.random() * MAX_COORDENADA_Y_DE_UBICACION;
    this.color = color;
  }

  public void dibujar(Graphics g) {
    g.setColor(color);
    g.fillRect(x, y, 5, 5);
    g.fillOval(x+3, y-7, 8, 8);
    g.fillOval(x-7, y-7, 8, 8);
  }

  public void calcularEstadoSiguiente() {
    pasos = ((int) (Math.random() * MAX_LONGITUD_DEL_PASO + 1));
    
    x = calcularCoordenada(x, MAX_COORDENADA_X_DE_UBICACION);
    y = calcularCoordenada(y, MAX_COORDENADA_Y_DE_UBICACION);
  }

  private int calcularCoordenada(int x, int maxCoordenadaPermitida) {
    return (x > maxCoordenadaPermitida || x < 0) ? (int) Math.random() * maxCoordenadaPermitida : x + pasos * (int) Math.pow(-1.0, (double) ((int) (Math.random() * 2) + 1));
  }
}
