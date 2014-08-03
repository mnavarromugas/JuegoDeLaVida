package com.navarromugas.test;

import com.navarromugas.models.Celula;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class CelulaTest {

	private Celula celula;

	@Before
	public void setUp() {
		celula = new Celula();
	}

	@Test
	public void una_celula_nueva_esta_muerta() {
		assertThat(celula.isViva(), equalTo(false));
	}

	@Test
	public void puedo_revivir_una_celula() {
		celula.revivir();
		assertThat(celula.isViva(), equalTo(true));
	}

	@Test
	public void puedo_matar_una_celula() {
		celula.revivir();
		celula.matar();
		assertThat(celula.isViva(), equalTo(false));
	}
}
