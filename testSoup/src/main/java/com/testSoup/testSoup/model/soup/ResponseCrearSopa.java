package com.testSoup.testSoup.model.soup;

import java.util.List;

public class ResponseCrearSopa {
	private String identificador;
	private char[][] sopa;
	private List<PalabrasAgregadas> palabras;
	
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public char[][] getSopa() {
		return sopa;
	}
	public void setSopa(char[][] sopa) {
		this.sopa = sopa;
	}
	public List<PalabrasAgregadas> getPalabras() {
		return palabras;
	}
	public void setPalabras(List<PalabrasAgregadas> palabras) {
		this.palabras = palabras;
	}
	
}
