package com.navarromugas.controllers;

import com.navarromugas.models.*;
import com.navarromugas.views.*;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;

/**
 @author Martin
 */
public class JuegoDeLaVida implements ChangeListener {

	private VentanaJuegoDeLaVida ventana;
	private PanelSimulador simulador;
	private GrillaPresenter gp;

	public JuegoDeLaVida() {
		ventana = new VentanaJuegoDeLaVida();
		gp = new GrillaPresenter();
		simulador = new PanelSimulador(new Grilla(gp));
		//simulador = new PanelSimulador(new Enjambre());

		inicializarSimulador();
		agregarManejadores();

		ventana.mostrar();
		correrSimulacion();
	}

	public static void main(String[] args) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JuegoDeLaVida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JuegoDeLaVida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JuegoDeLaVida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JuegoDeLaVida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JuegoDeLaVida();
			}
		});
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Object fuente = e.getSource();
		if (fuente instanceof JSlider)
			manejarCambiosDelSlider((JSlider) fuente);
	}

	private void inicializarSimulador() {
		ventana.agregarSimulador(simulador);
	}

	private void agregarManejadores() {
		ventana.agregarObservadorDeCambiosVelocidadDeSimulacion(this);

		ventana.agregarPanelSimuladorMouseListeners(
			new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					gp.mouseAction(e, true);
				}
			},
			new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					gp.mouseAction(e, false);
				}
			});

		ventana.agregarAccionCorrerSimulacion(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					simulador.reanudarSimulacion();
				}
			}
		);

		ventana.agregarAccionPausarSimulacion(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					simulador.pausarSimulacion();
				}
			}
		);

		ventana.agregarAccionAvanzarUnaGeneracion(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					simulador.avanzarUnCuadro();
				}
			}
		);

		ventana.agregarAccionRevivirCelulasAlAzar(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					gp.revivirCelulasAlAzar();
				}
			}
		);

		ventana.agregarAccionMatarTodasLasCelulas(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					gp.matarTodasLasCelulas();
				}
			}
		);

		ventana.agregarAccionGuardarMundo(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					JFileChooser destinationDialog = new JFileChooser();
					int returnVal = destinationDialog.showSaveDialog(ventana);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File directorySelected = destinationDialog.getSelectedFile();
						gp.guardarMundo(directorySelected);
						JOptionPane.showMessageDialog(ventana, "Mundo guardado.");
					}
				}
			}
		);

		ventana.agregarAccionCargarMundo(
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					JFileChooser openDialog = new JFileChooser();
					int returnVal = openDialog.showOpenDialog(ventana);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File fileSelected = openDialog.getSelectedFile();
						gp.cargarMundo(fileSelected);
						JOptionPane.showMessageDialog(ventana, "Mundo cargado.");
					}
				}
			}
		);
	}

	private void correrSimulacion() {
		simulador.reanudarSimulacion();
	}

	private void manejarCambiosDelSlider(JSlider slider) {
		if (!slider.getValueIsAdjusting())
			simulador.variarVelocidad(valorPorcentual(slider));
	}

	private double valorPorcentual(JSlider slider) {
		return (double) slider.getValue() / (slider.getMaximum() - slider.getMinimum());
	}

}
