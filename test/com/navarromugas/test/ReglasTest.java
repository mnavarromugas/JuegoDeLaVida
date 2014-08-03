package com.navarromugas.test;

import com.navarromugas.models.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ReglasTest {

	private MatrizDeCelulas matriz;

	@Before
	public void setUp() {
		matriz = new MatrizDeCelulas(3, 3);
	}

	@Test
	public void una_celula_muerta_sin_vecinos_no_revivira_en_la_proxima_generacion() {
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
	}

	@Test
	public void una_celula_viva_morira_si_no_tiene_vecinos() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(1, 1, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
	}

	@Test
	public void una_celula_viva_morira_si_tiene_un_solo_vecino() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(1, 1, celulaViva);
		matriz.setCelula(0, 2, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
	}

	@Test
	public void una_celula_viva_seguira_viva_si_tiene_dos_o_tres_vecinos() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(1, 1, celulaViva);
		matriz.setCelula(0, 2, celulaViva);
		matriz.setCelula(2, 2, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(true));
		matriz.setCelula(2, 0, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(true));
	}

	@Test
	public void una_celula_viva_con_4_o_mas_vecinos_morira_por_sobrepoblacion() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(1, 1, celulaViva);
		matriz.setCelula(0, 2, celulaViva);
		matriz.setCelula(2, 2, celulaViva);
		matriz.setCelula(2, 0, celulaViva);
		matriz.setCelula(0, 0, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
		matriz.setCelula(0, 1, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
	}

	@Test
	public void una_celula_muerta_vivira_si_tiene_solo_tres_vecinos_por_reproduccion() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(0, 2, celulaViva);
		matriz.setCelula(2, 2, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
		matriz.setCelula(2, 0, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(true));
		matriz.setCelula(0, 1, celulaViva);
		assertThat(Reglas.isCelulaVivaEnProximaGeneracion(matriz), equalTo(false));
	}

}
