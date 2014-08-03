package com.navarromugas.models;

import java.io.Serializable;

public class MatrizDeCelulas implements Serializable {

	private Celula[][] m;
	int filas, columnas;

	public MatrizDeCelulas(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		m = new Celula[filas][columnas];
		init();
	}

	public void setCelula(int fila, int columna, Celula celula) {
		m[fila][columna] = celula;
	}

	public int filas() {
		return this.filas;
	}

	public int columnas() {
		return this.columnas;
	}

	public Celula getCelula(int fila, int columna) {
		return m[fila][columna];
	}

	private void init() {
		for (int fila = 0; fila < filas; fila++)
			for (int columna = 0; columna < columnas; columna++)
				m[fila][columna] = new Celula();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int fila = 0; fila < filas; fila++) {
			for (int columna = 0; columna < columnas; columna++)
				sb.append("<" + m[fila][columna] + ">");
			sb.append("\n");
		}
		return sb.toString();
	}
}
