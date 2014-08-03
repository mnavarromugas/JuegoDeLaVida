package com.navarromugas.helpers;

import static com.navarromugas.helpers.ConversionesDeUnidadesDeTiempo.convertirNanoASegundos;
import java.awt.Color;
import java.awt.Graphics;

public class EstadisticasDelSimulador {
  private static final int TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO = 5;
  private static final long MAX_INTERVALO_ENTRE_ACTUALIZACIONES = (1000 / 2) * 1000000L; //cada medio segundo

  private long periodoDeCuadroEnNanosegundos, tiempoAnteriorALaActualizacionPrevia, intervaloEntreActualizaciones;
  private double[] registroDeFPS, registroDeUPS;
  private double FPSpromedio, UPSpromedio, fps, ups;
  private int valoresContabilizados, cantidadDeCuadrosDibujados, cuadrosSalteados;
  
  public EstadisticasDelSimulador(long periodoDeCuadroEnNanosegundos) {
    this.periodoDeCuadroEnNanosegundos = periodoDeCuadroEnNanosegundos;
    this.cantidadDeCuadrosDibujados = 0;
    this.intervaloEntreActualizaciones = 0;
    this.valoresContabilizados = 0;
    this.UPSpromedio = 0;
    this.FPSpromedio = 0;
    
    inicializarElementosParaMedirLosTiempos();
  }
  
  public void actualizar() {
    cantidadDeCuadrosDibujados++;
    intervaloEntreActualizaciones += periodoDeCuadroEnNanosegundos;

    if (intervaloEntreActualizaciones >= MAX_INTERVALO_ENTRE_ACTUALIZACIONES) {
      obtenerValoresEstadisticos();
    }
  }
	
	public void actualizarPeriodoDeCuadro(long periodoDeCuadroEnNanosegundos) {
		this.periodoDeCuadroEnNanosegundos = periodoDeCuadroEnNanosegundos;
	}

  public void dibujar(Graphics dbg) {
    dbg.setColor(Color.red);
    String info = String.format("FPS: %5.1f UPS: %5.1f FSK: %d", FPSpromedio, UPSpromedio, cuadrosSalteados);
    dbg.drawString(info, 20, 25);
  }
  
  public void saltearCuadros(int saltear) {
    cuadrosSalteados += saltear;
  }

  private void obtenerValoresEstadisticos() {
    long tiempoPosteriorALaActualizacionPrevia = System.nanoTime();
    long tiempoDeActualizacion = tiempoPosteriorALaActualizacionPrevia - tiempoAnteriorALaActualizacionPrevia;
    
    calcularValoresDeFPSyUPS(tiempoDeActualizacion);
    contabilizarValoresObtenidosDeFPSyUPS();
    calcularPromediosDeFPSyUPS();
    limpiarIndicadoresAuxiliares();    
    tiempoAnteriorALaActualizacionPrevia = tiempoPosteriorALaActualizacionPrevia;
  }

  private void calcularValoresDeFPSyUPS(long tiempoDeActualizacion) {
    fps = 0;
    ups = 0;
    if (tiempoDeActualizacion > 0) {
      fps = cantidadDeCuadrosDibujados / convertirNanoASegundos(tiempoDeActualizacion);
      ups = (cantidadDeCuadrosDibujados + cuadrosSalteados) / convertirNanoASegundos(tiempoDeActualizacion);
    }
  }

  private void contabilizarValoresObtenidosDeFPSyUPS() {
    registroDeFPS[ (int) valoresContabilizados % TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO] = fps;
    registroDeUPS[ (int) valoresContabilizados % TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO] = ups;
    valoresContabilizados += 1;
  }

  private void calcularPromediosDeFPSyUPS() {
    double sumaTotalDeFPSRegistrados = 0.0;
    double sumaTotalDeUPSRegistrados = 0.0;
    for (int i = 0; i < TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO; i++) {
      sumaTotalDeFPSRegistrados += registroDeFPS[i];
      sumaTotalDeUPSRegistrados += registroDeUPS[i];
    }
    
    if (valoresContabilizados < TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO) { // obtain the average FPS and UPS
      FPSpromedio = sumaTotalDeFPSRegistrados / valoresContabilizados;
      UPSpromedio = sumaTotalDeUPSRegistrados / valoresContabilizados;
    } else {
      FPSpromedio = sumaTotalDeFPSRegistrados / TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO;
      UPSpromedio = sumaTotalDeUPSRegistrados / TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO;
    }
  }
  
  private void limpiarIndicadoresAuxiliares() {
    cuadrosSalteados = 0;
    cantidadDeCuadrosDibujados = 0;
    intervaloEntreActualizaciones = 0;
  }
  
  private void inicializarElementosParaMedirLosTiempos() {
    registroDeFPS = new double[TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO];
    registroDeUPS = new double[TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO];
    inicializarEstructurasParaLasMuestras();
  }

  private void inicializarEstructurasParaLasMuestras() {
    for (int i = 0; i < TAMANO_DE_MUESTRA_PARA_EL_CALCULO_DEL_PROMEDIO; i++) {
      registroDeFPS[i] = 0.0;
      registroDeUPS[i] = 0.0;
    }
  }
}
