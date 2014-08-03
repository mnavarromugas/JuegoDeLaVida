package com.navarromugas.helpers;

import com.navarromugas.views.PanelSimulador;

public class VelocidadDeSimulacion {

	private static final int MAX_FPS_PERMITIDO = 100;
	private static final int MIN_FPS_PERMITIDO = 1;
	private static final int FPS_POR_DEFECTO = 10;

	private int FPS; //Cuadros por segundo (Frames per second).
	private long periodoEnMilisegundos; //Tiempo por cada cuadro en milisegundos.

	public VelocidadDeSimulacion(double valorPorcentual) {
		calcularFPS(valorPorcentual);
	}

	public void actualizarFPS(double valorPorcentual) {
		calcularFPS(valorPorcentual);
	}

	public long getPeriodoDeCuadroEnNanosegundos() {
		periodoEnMilisegundos = 1000 / FPS; //1 segundo = 1000 ms.
		return periodoEnMilisegundos * 1000000;
	}

	private void calcularFPS(double valorPorcentual) {
		try {
			double b = MIN_FPS_PERMITIDO - MAX_FPS_PERMITIDO;
			double a = FPS_POR_DEFECTO - MIN_FPS_PERMITIDO;
			double c = MAX_FPS_PERMITIDO - FPS_POR_DEFECTO;

			double u = calcularRaices(a, b, c);

			double r = 2 * Math.log(u);
			double q = a / (Math.exp(r / 2) - 1);
			double p = MIN_FPS_PERMITIDO - q;
			FPS = (int) funcionMonotonaExponencial(p, q, r, valorPorcentual);
		} catch (discriminanteNegativoException ex) {
			FPS = FPS_POR_DEFECTO;
		}
	}

	private double funcionMonotonaExponencial(double p, double q, double r, double x) {
		return p + q * Math.exp(r * x);
	}

	private double calcularRaices(double a, double b, double c) throws discriminanteNegativoException {
		double u = bascara(a, b, c, false);
		if (Math.log(u) == 0.0) {
			u = bascara(a, b, c, true);
		}
		return u;
	}

	private double bascara(double a, double b, double c, boolean raizCuadradaNegativa) throws discriminanteNegativoException {
		double discriminante = Math.pow(b, 2) - 4 * a * c;
		if (discriminante < 0)
			throw new discriminanteNegativoException();
		double raizCuadrada = Math.sqrt(discriminante);
		if (raizCuadradaNegativa)
			raizCuadrada *= -1;
		return (-b + raizCuadrada) / (2 * a);
	}

	private static class discriminanteNegativoException extends Exception {
	}

}
