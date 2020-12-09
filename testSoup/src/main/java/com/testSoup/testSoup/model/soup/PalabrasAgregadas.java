package com.testSoup.testSoup.model.soup;

public class PalabrasAgregadas {
	private String palabra;
	private Integer orientacion;
	private Integer fila;
	private Integer columna;
	
	public String getPalabra() {
		return palabra;
	}
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	public Integer getOrientacion() {
		return orientacion;
	}
	public void setOrientacion(Integer orientacion) {
		this.orientacion = orientacion;
	}
	public Integer getFila() {
		return fila;
	}
	public void setFila(Integer fila) {
		this.fila = fila;
	}
	public Integer getColumna() {
		return columna;
	}
	public void setColumna(Integer columna) {
		this.columna = columna;
	}
	@Override
	public String toString() {
		return "PalabrasAgregadas [palabra=" + palabra + ", orientacion=" + orientacion + ", fila=" + fila
				+ ", columna=" + columna + "]";
	}
	
}
