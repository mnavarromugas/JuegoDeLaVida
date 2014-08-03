package com.navarromugas.views;

import com.navarromugas.helpers.VelocidadDeSimulacion;
import com.navarromugas.helpers.EstadisticasDelSimulador;
import com.navarromugas.models.*;
import static com.navarromugas.helpers.ConversionesDeUnidadesDeTiempo.*;

import javax.swing.*;
import java.awt.*;

public class PanelSimulador extends JPanel implements Runnable {

	private EstadisticasDelSimulador estadisticasDelSimulador;

	private static final int MAX_CUADROS_SALTEABLES = 10;
	private static final int CANTIDAD_DE_CICLOS_SIN_DEMORAR_EJECUCION_DE_OTROS_HILOS = 5;

	private Thread simulador;
	private volatile boolean corriendoSimulacion = false; //volatile para que todos los hilos compartan la variable
	private volatile boolean enPausa = false;

	private long periodoDeCuadroEnNanosegundos;

	private long tiempoAnteriorAlCicloPrevio;
	private long tiempoDeDescansoSobrante = 0;
	private long excesoDeDescansoEnElCicloPrevio = 0;

	private Image dbImage;
	private Graphics dbg;
	private Simulable bastidor;
	private VelocidadDeSimulacion velocidadDeSimulacion;

	public PanelSimulador(Simulable bastidor) {
		this.bastidor = bastidor;
		setBackground(Color.white); //Fondo blanco :)

		double cincuentaPorCiento = 0.5;
		velocidadDeSimulacion = new VelocidadDeSimulacion(cincuentaPorCiento);
		actualizarPeriodoDeCuadro();
		estadisticasDelSimulador = new EstadisticasDelSimulador(periodoDeCuadroEnNanosegundos);
	}

	public void pausarSimulacion() {
		enPausa = true;
	}

	public void reanudarSimulacion() {
		despausarSimulacion();
		comenzarSimulacion();
	}

	public void pararSimulacion() {
		corriendoSimulacion = false;
	}
	
	public void avanzarUnCuadro() {
		despausarSimulacion();
		avanzarSimulacion();
		pausarSimulacion();
	}

	public void variarVelocidad(double valorPorcentualDelSlider) {
		velocidadDeSimulacion.actualizarFPS(valorPorcentualDelSlider);
		actualizarPeriodoDeCuadro();
	}

	@Override
	public void run() {
		tomarTiempoActual();

		corriendoSimulacion = true;
		while (corriendoSimulacion) {
			avanzarSimulacion();
			dormir(); //Tiempo de espera
			estadisticasDelSimulador.actualizar();
		}
	}

	private void actualizarPeriodoDeCuadro() {
		periodoDeCuadroEnNanosegundos = velocidadDeSimulacion.getPeriodoDeCuadroEnNanosegundos();
	}

	private void despausarSimulacion() {
		enPausa = false;
	}

	private void avanzarSimulacion() {
		actualizarEstadoDeElementosGraficables();
		renderizarEnBuffer();
		dibujarPantallaUsandoElBuffer();
	}

	private void comenzarSimulacion() {
		if (simulador == null || !corriendoSimulacion) {
			simulador = new Thread(this); //Inicializa un nuevo hilo para la simulacion
			simulador.start(); //Ejecuta el hilo
		}
	}

	private void actualizarEstadoDeElementosGraficables() {
		if (!enPausa) {
			calcularEstadosSiguientes();
		}
	}

	private void renderizarEnBuffer() {
		crearBuffer();
		limpiarFondo();
		dibujarElementos();
		estadisticasDelSimulador.dibujar(dbg);
	}

	private void dibujarPantallaUsandoElBuffer() {
		Graphics g;
		try {
			g = this.getGraphics(); //Obtiene el contexto grafico del panel
			if ((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null); //dibuja el buffer
			}
			Toolkit.getDefaultToolkit().sync(); //Sincroniza la pantalla
			g.dispose();
		} catch (Exception e) {
			System.out.println("Problemas con los graficos: " + e);
		}
	}

	private void dormir() {
		long tiempoPosteriorAlCicloPrevio = System.nanoTime();
		long tiempoDeProcesamientoDelCicloAnterior = tiempoPosteriorAlCicloPrevio - tiempoAnteriorAlCicloPrevio;

		long tiempoDeDescanso = (periodoDeCuadroEnNanosegundos - tiempoDeProcesamientoDelCicloAnterior) - tiempoDeDescansoSobrante;
		if (tiempoDeDescanso > 0) {
			dormirHiloDeEjecucion(tiempoDeDescanso);
			calcularDescansoSobrante(tiempoDeDescanso, tiempoPosteriorAlCicloPrevio);
		} else {
			excesoDeDescansoEnElCicloPrevio -= tiempoDeDescanso;
			tiempoDeDescansoSobrante = 0; //porque ya nos pasamos
			ejecutarOtrosHilosSinInterrumpirlos();
		}

		tiempoAnteriorAlCicloPrevio = System.nanoTime();

		saltearCuadrosSiHaceFalta();
	}

	private void calcularEstadosSiguientes() {
		bastidor.calcularEstadoSiguiente();
	}

	private void crearBuffer() {
		if (dbImage == null) {
			dbImage = createImage(getWidth(), getHeight()); //Crea el buffer
			if (dbImage != null) {
				dbg = dbImage.getGraphics();
			}
		}
	}

	private void limpiarFondo() {
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, getWidth(), getHeight());
	}

	private void dibujarElementos() {
		bastidor.dibujar(dbg);
	}

	private void dormirHiloDeEjecucion(long tiempoDeDescanso) {
		try {
			Thread.sleep(convertirNanoAMili(tiempoDeDescanso)); //pausa
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void calcularDescansoSobrante(long tiempoDeDescanso, long tiempoPosteriorDelCicloActual) {
		tiempoDeDescansoSobrante = (System.nanoTime() - tiempoPosteriorDelCicloActual) - tiempoDeDescanso;
	}

	private void ejecutarOtrosHilosSinInterrumpirlos() {
		int sinDemoras = 0;
		while (CANTIDAD_DE_CICLOS_SIN_DEMORAR_EJECUCION_DE_OTROS_HILOS > sinDemoras) {
			Thread.yield(); //Deja que otros hilos se puedan ejecutar
			sinDemoras++;
		}
	}

	private void saltearCuadrosSiHaceFalta() {
		int saltear = 0;
		while ((excesoDeDescansoEnElCicloPrevio > periodoDeCuadroEnNanosegundos) && (saltear < MAX_CUADROS_SALTEABLES)) {
			excesoDeDescansoEnElCicloPrevio -= periodoDeCuadroEnNanosegundos;
			actualizarEstadoDeElementosGraficables();
			saltear++;
		}
		estadisticasDelSimulador.saltearCuadros(saltear);
	}

	private void tomarTiempoActual() {
		tiempoAnteriorAlCicloPrevio = System.nanoTime();
	}

}
