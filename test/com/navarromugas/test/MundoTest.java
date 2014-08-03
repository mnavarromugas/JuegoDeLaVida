package com.navarromugas.test;

import com.navarromugas.models.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class MundoTest {

	private Mundo mundo;

	@Before
	public void setUp() {
		mundo = new Mundo(new Gps(3, 3));
	}

	@Test
	public void puedo_ver_el_estado_de_una_celula_en_alguna_parte_del_mundo() {
		Coordenada unaUbicacionCualquiera = new Coordenada(0, 0);
		assertThat(mundo.isCelulaViva(unaUbicacionCualquiera), equalTo(false));
	}

	@Test
	public void si_revivo_una_celula_en_el_mundo_esta_debe_estar_viva() {
		Coordenada unaUbicacionCualquiera = new Coordenada(1, 1);
		mundo.revivirCelula(unaUbicacionCualquiera);
		assertThat(mundo.isCelulaViva(unaUbicacionCualquiera), equalTo(true));
	}

	@Test
	public void puedo_obtener_una_parcela_de_una_celula_viva_de_3x3_con_ella_como_referencia() {
		mundo.revivirCelula(new Coordenada(1, 1));
		MatrizDeCelulas parcela = mundo.getParcela(new Coordenada(1, 1));

		assertThat(parcela.getCelula(0, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(0, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(0, 2).isViva(), equalTo(false));
		assertThat(parcela.getCelula(1, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(1, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 2).isViva(), equalTo(false));
		assertThat(parcela.getCelula(2, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(2, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(2, 2).isViva(), equalTo(false));
	}

	@Test
	public void puedo_obtener_una_parcela_de_celulas_vivas_en_cruz_de_3x3() {
		mundo.revivirCelula(new Coordenada(0, 1));
		mundo.revivirCelula(new Coordenada(1, 1));
		mundo.revivirCelula(new Coordenada(2, 1));
		mundo.revivirCelula(new Coordenada(1, 0));
		mundo.revivirCelula(new Coordenada(1, 2));

		MatrizDeCelulas parcela = mundo.getParcela(new Coordenada(0, 0));

		assertThat(parcela.getCelula(0, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(0, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(0, 2).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(1, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(1, 2).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 2).isViva(), equalTo(true));
	}

	@Test
	public void puedo_obtener_una_parcela_de_celulas_vivas_de_3x3() {
		mundo.revivirCelula(new Coordenada(0, 0));
		mundo.revivirCelula(new Coordenada(0, 1));
		mundo.revivirCelula(new Coordenada(0, 2));
		mundo.revivirCelula(new Coordenada(1, 0));
		mundo.revivirCelula(new Coordenada(1, 1));
		mundo.revivirCelula(new Coordenada(1, 2));
		mundo.revivirCelula(new Coordenada(2, 0));
		mundo.revivirCelula(new Coordenada(2, 1));
		mundo.revivirCelula(new Coordenada(2, 2));

		MatrizDeCelulas parcela = mundo.getParcela(new Coordenada(1, 1));

		assertThat(parcela.getCelula(0, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(0, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(0, 2).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 2).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 2).isViva(), equalTo(true));
	}

	@Test
	public void puedo_obtener_una_parcela_de_3x3_del_mundo_usando_como_referencia_una_celula_central() {
		mundo = new Mundo(new Gps(4, 4));
		mundo.revivirCelula(new Coordenada(1, 1));
		mundo.revivirCelula(new Coordenada(3, 1));
		mundo.revivirCelula(new Coordenada(1, 3));
		mundo.revivirCelula(new Coordenada(2, 2));
		mundo.revivirCelula(new Coordenada(3, 3));

		MatrizDeCelulas parcela = mundo.getParcela(new Coordenada(2, 2));

		assertThat(parcela.getCelula(0, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(0, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(0, 2).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 0).isViva(), equalTo(false));
		assertThat(parcela.getCelula(1, 1).isViva(), equalTo(true));
		assertThat(parcela.getCelula(1, 2).isViva(), equalTo(false));
		assertThat(parcela.getCelula(2, 0).isViva(), equalTo(true));
		assertThat(parcela.getCelula(2, 1).isViva(), equalTo(false));
		assertThat(parcela.getCelula(2, 2).isViva(), equalTo(true));
	}

	@Test
	public void puedo_clonar_mundos() {
		mundo.revivirCelula(new Coordenada(0, 0));
		mundo.revivirCelula(new Coordenada(0, 1));
		mundo.revivirCelula(new Coordenada(0, 2));
		mundo.revivirCelula(new Coordenada(1, 0));
		mundo.revivirCelula(new Coordenada(1, 1));
		mundo.revivirCelula(new Coordenada(1, 2));
		mundo.revivirCelula(new Coordenada(2, 0));
		mundo.revivirCelula(new Coordenada(2, 1));
		mundo.revivirCelula(new Coordenada(2, 2));

		Mundo nuevoMundo = mundo.clonar();

		assertThat(nuevoMundo.isCelulaViva(new Coordenada(0, 0)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(0, 1)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(0, 2)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(1, 0)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(1, 1)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(1, 2)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(2, 0)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(2, 1)), equalTo(true));
		assertThat(nuevoMundo.isCelulaViva(new Coordenada(2, 2)), equalTo(true));
	}

	@Test
	public void puedo_obtener_la_matriz_de_celulas() {
		MatrizDeCelulas matriz = mundo.getMatrizDeCelulas();
		assertThat(matriz.filas(), equalTo(3));
		assertThat(matriz.columnas(), equalTo(3));
	}
}
