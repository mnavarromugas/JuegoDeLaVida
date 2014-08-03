package com.navarromugas.test;

import com.navarromugas.models.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GpsTest {

	private final int PARALELOS = 10;
	private final int MERIDIANOS = 10;
	Gps gps;

	@Before
	public void setUp() {
		gps = new Gps(PARALELOS, MERIDIANOS);
	}

	@Test
	public void puedo_obtener_los_paralelos_y_meridianos_del_gps() {
		assertThat(gps.getParalelos(), equalTo(PARALELOS));
		assertThat(gps.getMeridianos(), equalTo(MERIDIANOS));
	}
	
	@Test
	public void puede_devolver_la_coordenada_al_norte_relativa_a_otra() {
		assertThat(gps.norte(new Coordenada(1, 1)), equalTo(new Coordenada(0, 1)));
	}

	@Test
	public void puede_devolver_la_coordenada_dos_lugares_al_norte_relativa_a_otra() {
		assertThat(gps.norte(gps.norte(new Coordenada(2, 2))), equalTo(new Coordenada(0, 2)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_sur_relativa_a_otra() {
		assertThat(gps.sur(new Coordenada(1, 1)), equalTo(new Coordenada(2, 1)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_este_relativa_a_otra() {
		assertThat(gps.este(new Coordenada(1, 1)), equalTo(new Coordenada(1, 2)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_oeste_relativa_a_otra() {
		assertThat(gps.oeste(new Coordenada(1, 1)), equalTo(new Coordenada(1, 0)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_noroeste_relativa_a_otra() {
		assertThat(gps.noroeste(new Coordenada(1, 1)), equalTo(new Coordenada(0, 0)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_noreste_relativa_a_otra() {
		assertThat(gps.noreste(new Coordenada(1, 1)), equalTo(new Coordenada(0, 2)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_sureste_relativa_a_otra() {
		assertThat(gps.sureste(new Coordenada(1, 1)), equalTo(new Coordenada(2, 2)));
	}

	@Test
	public void puede_devolver_la_coordenada_al_suroeste_relativa_a_otra() {
		assertThat(gps.suroeste(new Coordenada(1, 1)), equalTo(new Coordenada(2, 0)));
	}

	@Test
	public void puede_devolver_la_coordenada_valida_a_un_mundo_toroidal() {
		assertThat(gps.norte(new Coordenada(0, 1)), equalTo(new Coordenada(PARALELOS - 1, 1)));
	}

	@Test
	public void puede_devolver_la_coordenada_valida_a_un_mundo_toroidal_al_noroeste() {
		assertThat(gps.noroeste(new Coordenada(0, 0)), equalTo(new Coordenada(PARALELOS - 1, MERIDIANOS - 1)));
	}

	@Test
	public void puede_devolver_la_coordenada_valida_a_un_mundo_toroidal_al_noreste() {
		assertThat(gps.noreste(new Coordenada(0, MERIDIANOS - 1)), equalTo(new Coordenada(PARALELOS - 1, 0)));
	}

	@Test
	public void puede_devolver_la_coordenada_valida_a_un_mundo_toroidal_al_sureste() {
		assertThat(gps.sureste(new Coordenada(PARALELOS - 1, MERIDIANOS - 1)), equalTo(new Coordenada(0, 0)));
	}

	@Test
	public void puede_devolver_la_coordenada_valida_a_un_mundo_toroidal_al_suroeste() {
		assertThat(gps.suroeste(new Coordenada(PARALELOS - 1, 0)), equalTo(new Coordenada(0, MERIDIANOS - 1)));
	}

}
