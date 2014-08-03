package com.navarromugas.models;

public class VecinidadDeMoore {

	public static int cantidadDeVecinos(MatrizDeCelulas matriz) {
		int vecinos = 0;

		for (int i = 0; i < matriz.filas(); i++)
			for (int j = 0; j < matriz.filas(); j++) {
				if ((i != 1 || j != 1) && matriz.getCelula(i, j).isViva())
					vecinos++;
			}
		return vecinos;
	}
}
