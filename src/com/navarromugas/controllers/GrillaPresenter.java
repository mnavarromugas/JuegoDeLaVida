package com.navarromugas.controllers;

import com.navarromugas.models.*;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.*;

public class GrillaPresenter {

	private Dios dios;
	private int filas, columnas;
	private Grilla grilla;

	private FileOutputStream FOS;
	private ObjectOutputStream OOS;
	private FileInputStream FIS;
	private ObjectInputStream OIS;

	public GrillaPresenter() {
		filas = Grilla.FILAS;
		columnas = Grilla.COLUMNAS;
		dios = new Dios(new Gps(filas, columnas));
	}

	public void setGrilla(Grilla grilla) {
		this.grilla = grilla;
	}

	public boolean isCelulaVivaEnElMundoCreadoPorDios(int fila, int columna) {
		return dios.mundoCreado().getMatrizDeCelulas().getCelula(fila, columna).isViva();
	}

	public void calcularEstadoSiguiente() {
		dios.avanzarUnaGeneracion();
	}

	public void revivirCelulasAlAzar() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				if (verdaderoOFalsoAlAzar())
					dios.mundoCreado().revivirCelula(new Coordenada(i, j));
			}
		}
	}

	public void matarTodasLasCelulas() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				dios.mundoCreado().matarCelula(new Coordenada(i, j));
			}
		}
	}

	public void guardarMundo(File file) {
		try {
			guardarMundoEnUbicacion(file);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void cargarMundo(File file) {
		try {
			cargarMundoDesdeUbicacion(file);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private static Coordenada coordenadaAnterior;

	public void mouseAction(MouseEvent e, boolean isClicked) {
		Point point = e.getPoint();
		Coordenada coordenada = grilla.getCoordenada(point);
		boolean isNuevaCoordenada = (coordenadaAnterior != null && !coordenadaAnterior.equals(coordenada)) ? true : false;
		if (isPuntoEntreLosLimitesPermitidosEnLaGrilla(point) && (isNuevaCoordenada || isClicked))
			toggleCelula(coordenada);
		coordenadaAnterior = coordenada;
	}

	private void guardarMundoEnUbicacion(File file) throws IOException, FileNotFoundException {
		FOS = new FileOutputStream(file);
		OOS = new ObjectOutputStream(FOS);

		OOS.writeObject(dios.mundoCreado());

		OOS.close();
		FOS.close();
	}

	private void cargarMundoDesdeUbicacion(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
		FIS = new FileInputStream(file);
		OIS = new ObjectInputStream(FIS);

		Mundo mundoCargado = (Mundo) OIS.readObject();
		dios.darMundo(mundoCargado);

		OIS.close();
		FIS.close();
	}

	private boolean isPuntoEntreLosLimitesPermitidosEnLaGrilla(Point point) {
		return (point.x > grilla.getBounds().x) && (point.x < grilla.getBounds().width) && (point.y > grilla.getBounds().y) && (point.y < grilla.getBounds().height);
	}

	private void toggleCelula(Coordenada coordenada) {
		Mundo mundo = dios.mundoCreado();
		if (mundo.isCelulaViva(coordenada))
			mundo.matarCelula(coordenada);
		else
			mundo.revivirCelula(coordenada);
	}

	private boolean verdaderoOFalsoAlAzar() {
		return ((int) (Math.random() * 10) % 2 == 0);
	}
}
