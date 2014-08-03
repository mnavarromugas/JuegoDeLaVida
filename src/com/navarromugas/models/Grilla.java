package com.navarromugas.models;

import com.navarromugas.controllers.GrillaPresenter;
import java.awt.*;

public class Grilla implements Simulable {

	public static final int FILAS = 96;
	public static final int COLUMNAS = 96;

	private Dimension dimension;
	private Color interiorDeCelula;
	private Color bordeDeCelula;

	private int anchoDelBastidor, altoDelBastidor;
	private int distanciaEntreColumnas, distanciaEntreFilas;
	private int corrimientoEnXParaCentrado, corrimientoEnYParaCentrado;

	private GrillaPresenter gp;

	public Grilla(GrillaPresenter gp) {
		this.gp = gp;
		initElementosGraficos();
		calcularCotas();
		pasarReferenciaDeGrillaAPresenter();
	}

	@Override
	public void dibujar(Graphics g) {
		dibujarBordeDelBastidor(g);
		for (int i = 0; i < FILAS; i++)
			for (int j = 0; j < COLUMNAS; j++) {
				if (gp.isCelulaVivaEnElMundoCreadoPorDios(i, j)) {
					dibujarInteriorDeCelula(i, j, g);
					dibujarBordeDeCelula(i, j, g);
				}
			}
	}

	@Override
	public void calcularEstadoSiguiente() {
		gp.calcularEstadoSiguiente();
	}

	public Rectangle getBounds() {
		return new Rectangle(corrimientoEnXParaCentrado, corrimientoEnYParaCentrado, (distanciaEntreColumnas * COLUMNAS) + corrimientoEnXParaCentrado, (distanciaEntreFilas * FILAS) + corrimientoEnYParaCentrado);
	}

	public Coordenada getCoordenada(Point point) {
		int fila = (FILAS - 1) - ((altoDelBastidor - point.y - corrimientoEnYParaCentrado) / distanciaEntreFilas);
		int columna = (COLUMNAS - 1) - ((anchoDelBastidor - point.x - corrimientoEnXParaCentrado) / distanciaEntreColumnas);
		return new Coordenada(fila, columna);
	}

	private void dibujarBordeDelBastidor(Graphics g) {
		g.setColor(Color.lightGray);
		g.drawRect(0, 0, anchoDelBastidor, altoDelBastidor);
	}

	private void dibujarBordeDeCelula(int fila, int columna, Graphics g) {
		g.setColor(bordeDeCelula);
		g.drawRect(columna * distanciaEntreColumnas + corrimientoEnXParaCentrado, fila * distanciaEntreFilas + corrimientoEnYParaCentrado, distanciaEntreColumnas, distanciaEntreFilas);
	}

	private void dibujarInteriorDeCelula(int fila, int columna, Graphics g) {
		g.setColor(interiorDeCelula);
		g.fillRect(columna * distanciaEntreColumnas + corrimientoEnXParaCentrado, fila * distanciaEntreFilas + corrimientoEnYParaCentrado, distanciaEntreColumnas, distanciaEntreFilas);
	}

	private void initElementosGraficos() {
		dimension = new Dimension(480, 480);
		interiorDeCelula = new Color(162, 255, 204);
		bordeDeCelula = new Color(12, 178, 88);
	}

	private void calcularCotas() {
		anchoDelBastidor = (int) dimension.getWidth();
		distanciaEntreColumnas = anchoDelBastidor / COLUMNAS;
		altoDelBastidor = (int) dimension.getHeight();
		distanciaEntreFilas = altoDelBastidor / FILAS;
		corrimientoEnXParaCentrado = (int) (anchoDelBastidor - (distanciaEntreColumnas * COLUMNAS)) / 2;
		corrimientoEnYParaCentrado = (int) (altoDelBastidor - (distanciaEntreFilas * FILAS)) / 2;
	}

	private void pasarReferenciaDeGrillaAPresenter() {
		gp.setGrilla(this);
	}

}
