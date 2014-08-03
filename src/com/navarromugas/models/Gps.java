package com.navarromugas.models;

import java.io.Serializable;

public class Gps implements Serializable {

	private int paralelos, meridianos;

	public Gps(int paralelos, int meridianos) {
		this.paralelos = paralelos;
		this.meridianos = meridianos;
	}
	
	public int getParalelos() {
		return paralelos;
	}
	
	public int getMeridianos() {
		return meridianos;
	}
	
	public Coordenada norte(Coordenada referencia) {
		return recalculando(referencia, -1, 0);
	}

	public Coordenada sur(Coordenada referencia) {
		return recalculando(referencia, 1, 0);
	}

	public Coordenada este(Coordenada referencia) {
		return recalculando(referencia, 0, 1);
	}

	public Coordenada oeste(Coordenada referencia) {
		return recalculando(referencia, 0, -1);
	}

	public Coordenada noroeste(Coordenada referencia) {
		return recalculando(referencia, -1, -1);
	}

	public Coordenada noreste(Coordenada referencia) {
		return recalculando(referencia, -1, 1);
	}

	public Coordenada sureste(Coordenada referencia) {
		return recalculando(referencia, 1, 1);
	}

	public Coordenada suroeste(Coordenada referencia) {
		return recalculando(referencia, 1, -1);
	}

	private Coordenada recalculando(Coordenada referencia, int corrimientoPorParalelos, int corrimientoPorMeridianos) {
		int latitudCalculada = referencia.getLatitud() + corrimientoPorParalelos;
		latitudCalculada = ((latitudCalculada % paralelos) + paralelos) % paralelos;
		int longitudCalculada = referencia.getLongitud() + corrimientoPorMeridianos;
		longitudCalculada = ((longitudCalculada % meridianos) + meridianos) % meridianos;
		return new Coordenada(latitudCalculada, longitudCalculada);
	}
}
