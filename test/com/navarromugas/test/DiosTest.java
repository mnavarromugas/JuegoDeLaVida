package com.navarromugas.test;

import com.navarromugas.models.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class DiosTest {

	private Dios dios;
	private Mundo mundoCreadoPorDios;
	private Gps gps;

	@Before
	public void setUp() {
		gps = new Gps(3, 3);
		dios_existe();
		dios_creo_al_mundo();
	}

	private void dios_existe() {
		this.dios = new Dios(gps);
	}

	private void dios_creo_al_mundo() {
		mundoCreadoPorDios = dios.mundoCreado();
	}

	@Test
	public void a_dios_le_podemos_dar_un_mundo_existente_para_que_juegue_con_el() {
		Mundo mundo = new Mundo(new Gps(5, 5));
		dios.darMundo(mundo);
		assertThat(dios.mundoCreado().toString(), equalTo(mundo.toString()));
	}

	@Test
	public void si_hay_una_sola_celula_viva_en_la_proxima_generacion_muere() {
		Coordenada ubicacion = new Coordenada(2, 2);
		mundoCreadoPorDios.revivirCelula(ubicacion);
		dios.avanzarUnaGeneracion();
		assertThat(mundoCreadoPorDios.isCelulaViva(ubicacion), equalTo(false));
	}

	@Test
	public void una_celula_muerta_con_tres_vecinas_vivas_vive_por_reproduccion() {
		mundoCreadoPorDios.revivirCelula(new Coordenada(0, 0));
		mundoCreadoPorDios.revivirCelula(new Coordenada(0, 2));
		mundoCreadoPorDios.revivirCelula(new Coordenada(1, 2));
		dios.avanzarUnaGeneracion();
		assertThat(mundoCreadoPorDios.isCelulaViva(new Coordenada(1, 1)), equalTo(true));
	}

	@Test
	public void una_celula_muerta_con_tres_vecinas_vivas_en_un_mundo_toroidal_vive_por_reproduccion() {
		mundoCreadoPorDios.revivirCelula(new Coordenada(gps.getParalelos() - 1, gps.getMeridianos() - 1));
		mundoCreadoPorDios.revivirCelula(new Coordenada(1, 0));
		mundoCreadoPorDios.revivirCelula(new Coordenada(0, 1));
		dios.avanzarUnaGeneracion();
		assertThat(mundoCreadoPorDios.isCelulaViva(new Coordenada(0, 0)), equalTo(true));
	}
}
