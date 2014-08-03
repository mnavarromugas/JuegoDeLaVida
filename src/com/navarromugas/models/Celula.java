package com.navarromugas.models;

import java.io.Serializable;

public class Celula implements Serializable {

	private boolean viva = false;

	public boolean isViva() {
		return viva;
	}

	public void revivir() {
		viva = true;
	}

	public void matar() {
		viva = false;
	}

	@Override
	public String toString() {
		return viva ? "V" : "M";
	}
}
