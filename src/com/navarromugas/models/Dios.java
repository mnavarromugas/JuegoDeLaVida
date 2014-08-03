package com.navarromugas.models;

public class Dios {

	private Mundo mundo;
	private Gps gps;

	public Dios(Gps gps) {
		this.gps = gps;
		mundo = new Mundo(gps);
	}

	public void avanzarUnaGeneracion() {
		Mundo viejoMundo = mundo.clonar();
		for (int i = 0; i < gps.getParalelos(); i++)
			for (int j = 0; j < gps.getMeridianos(); j++) {
				Coordenada ubicacion = new Coordenada(i, j);
				if (Reglas.isCelulaVivaEnProximaGeneracion(viejoMundo.getParcela(ubicacion)))
					mundo.revivirCelula(ubicacion);
				else
					mundo.matarCelula(ubicacion);
			}
	}

	public Mundo mundoCreado() {
		return mundo;
	}

	public void darMundo(Mundo mundo) {
		this.mundo = mundo;
	}
}
