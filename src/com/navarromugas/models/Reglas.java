package com.navarromugas.models;

public class Reglas {

	public static boolean isCelulaVivaEnProximaGeneracion(MatrizDeCelulas matriz) {
		int vecinos = VecinidadDeMoore.cantidadDeVecinos(matriz);
		if ((matriz.getCelula(1,1).isViva()) && (vecinos == 2 || vecinos == 3)) return true;
		if ((!matriz.getCelula(1,1).isViva()) && (vecinos == 3)) return true;
		return false;
	}

}
