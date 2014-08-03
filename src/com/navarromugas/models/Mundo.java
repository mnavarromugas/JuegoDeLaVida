package com.navarromugas.models;

import java.io.Serializable;

public class Mundo implements Serializable {

	private MatrizDeCelulas celulas;
	private Gps gps;

	public Mundo(Gps gps) {
		this.gps = gps;
		celulas = new MatrizDeCelulas(gps.getParalelos(), gps.getMeridianos());
	}

	public void revivirCelula(Coordenada ubicacion) {
		celulas.getCelula(ubicacion.getLatitud(), ubicacion.getLongitud()).revivir();
	}

	public boolean isCelulaViva(Coordenada ubicacion) {
		return celulas.getCelula(ubicacion.getLatitud(), ubicacion.getLongitud()).isViva();
	}

	public void matarCelula(Coordenada ubicacion) {
		celulas.getCelula(ubicacion.getLatitud(), ubicacion.getLongitud()).matar();
	}

	public MatrizDeCelulas getParcela(Coordenada ubicacion) {
		MatrizDeCelulas parcela = new MatrizDeCelulas(3, 3);
		obtenerCelulasParaLaParcela(ubicacion, parcela);
		return parcela;
	}

	public Mundo clonar() {
		Mundo nuevoMundo = new Mundo(gps);
		for (int i = 0; i < gps.getParalelos(); i++)
			for (int j = 0; j < gps.getMeridianos(); j++) {
				Coordenada coordenada = new Coordenada(i, j);
				if (this.isCelulaViva(coordenada))
					nuevoMundo.revivirCelula(coordenada);
			}
		return nuevoMundo;
	}

	public MatrizDeCelulas getMatrizDeCelulas() {
		return celulas;
	}

	private void obtenerCelulasParaLaParcela(Coordenada ubicacion, MatrizDeCelulas parcela) {
		obtenerCelula(0, 0, gps.noroeste(ubicacion), parcela);
		obtenerCelula(0, 1, gps.norte(ubicacion), parcela);
		obtenerCelula(0, 2, gps.noreste(ubicacion), parcela);
		obtenerCelula(1, 0, gps.oeste(ubicacion), parcela);
		obtenerCelula(1, 1, ubicacion, parcela);
		obtenerCelula(1, 2, gps.este(ubicacion), parcela);
		obtenerCelula(2, 0, gps.suroeste(ubicacion), parcela);
		obtenerCelula(2, 1, gps.sur(ubicacion), parcela);
		obtenerCelula(2, 2, gps.sureste(ubicacion), parcela);
	}

	private void obtenerCelula(int fila, int columna, Coordenada ubicacionCalculada, MatrizDeCelulas parcela) {
		parcela.setCelula(fila, columna, celulas.getCelula(ubicacionCalculada.getLatitud(), ubicacionCalculada.getLongitud()));
	}

}
