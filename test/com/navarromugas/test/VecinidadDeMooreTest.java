package com.navarromugas.test;

import com.navarromugas.models.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class VecinidadDeMooreTest {

	private MatrizDeCelulas matriz;

	@Before
	public void setUp() {
		matriz = new MatrizDeCelulas(3, 3);
	}

	@Test
	public void dada_una_matriz_cuadrada_de_3x3_me_devuelve_la_cantidad_de_vecinos_de_la_celula_central() {
		assertThat(VecinidadDeMoore.cantidadDeVecinos(matriz), equalTo(0));
	}

	@Test
	public void para_la_cantidad_de_vecinos_el_elemento_central_en_una_matriz_3x3_es_irrelevante() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(1, 1, celulaViva);
		assertThat(VecinidadDeMoore.cantidadDeVecinos(matriz), equalTo(0));
	}

	@Test
	public void devuelve_la_cantidad_correcta_con_un_unico_vecino() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(0, 0, celulaViva);
		assertThat(VecinidadDeMoore.cantidadDeVecinos(matriz), equalTo(1));
	}

	@Test
	public void devuelve_la_cantidad_correcta_con_tres_vecinos() {
		Celula celulaViva = new Celula();
		celulaViva.revivir();
		matriz.setCelula(0, 0, celulaViva);
		matriz.setCelula(2, 2, celulaViva);
		matriz.setCelula(1, 2, celulaViva);
		assertThat(VecinidadDeMoore.cantidadDeVecinos(matriz), equalTo(3));
	}
}
