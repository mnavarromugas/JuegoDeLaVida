package com.navarromugas.models;

public class Coordenada {

	private int latitud, longitud;

	public Coordenada(int latitud, int longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public int getLatitud() {
		return latitud;
	}

	public int getLongitud() {
		return longitud;
	}

	@Override
	public boolean equals(Object obj) {
		return (((Coordenada) obj).latitud == latitud) && (((Coordenada) obj).longitud == longitud);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(latitud).append(", ").append(longitud).append(")");
		return sb.toString();
	}
}
