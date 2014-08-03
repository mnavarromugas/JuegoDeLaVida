package com.navarromugas.helpers;

public class ConversionesDeUnidadesDeTiempo {
  public static double convertirNanoASegundos(long nanosegundos) {
    return (double) nanosegundos / (1000000L * 1000);
  }

  public static long convertirNanoAMili(long nanosegundos) {
    return nanosegundos / 1000000L;
  }
}
