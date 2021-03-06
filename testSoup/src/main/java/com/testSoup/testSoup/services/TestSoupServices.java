package com.testSoup.testSoup.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.configuration.PropertiesConfiguration;

import com.google.gson.Gson;
import com.testSoup.testSoup.model.response.ResCrearSopa;
import com.testSoup.testSoup.model.resquest.ReqCrearSopa;
import com.testSoup.testSoup.model.resquest.ReqEncontrarPalabra;
import com.testSoup.testSoup.model.soup.PalabrasAgregadas;
import com.testSoup.testSoup.model.soup.ResponseCrearSopa;

public class TestSoupServices {

	@SuppressWarnings("finally")
	public ResponseCrearSopa crearSopa(ReqCrearSopa request) {

		ResponseCrearSopa resultado = new ResponseCrearSopa();
		try {
			
			char alfab[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
					's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
			char[][] sopa = new char[request.getW()][request.getH()];
			String [] palas = {"algebra","junco","internacional","abeja","abejorro","aguila","alce","ballena","caballo","cachalote","camaleona","camello","canario","cangrejo","canguro","capibara","caracol","castor","cervatillo","ciervo","cisne","cocodrilo","condor","cordero","elefante","foca","gallo","gaviota","gato","gorila","guanaco","halcon","hiena","hipopotamo","hormiga","huemul","jaguar","jaiba","jirafa","lagarto","langosta","leon","lobo","llama","medusa","mula","murcielago","ornitorrinco","perro","puma","rata","raton","serpiente","tapir","tiburon","tigre","toro","tortuga","zebra","zorro"};

			List<PalabrasAgregadas> palabras = new ArrayList<PalabrasAgregadas>();
			PalabrasAgregadas palabra = new PalabrasAgregadas();
			String pala = "";
			
			int valorPala = (int)Math.floor(Math.random()*60+1);

			pala = palas[valorPala];
			
			palabra.setPalabra(pala);

			Random r = new Random();
			int x, i, j, p, b;
			p = 0;
			b = 0;
			b = r.nextInt(3);

			for (i = 0; i < request.getW(); i++)
				for (j = 0; j < request.getH(); j++) {
					x = r.nextInt(26);
					sopa[j][i] = alfab[x];
				}
			
			palabra.setOrientacion(b);

			if (b == 0) {
				x = r.nextInt(3);
				for (i = 0; i < pala.length(); i++) {
					p = p + 1;
					sopa[x + p][i] = pala.charAt(i);

					if (i == 0) {
						palabra.setFila(i);
						palabra.setColumna(x + p);
					}
					if(i == (pala.length() - 1)) {
						palabra.setFilaFin(i);
						palabra.setColumnaFin(x + p);
					}
				}
			}
			if (b == 1) {
				x = r.nextInt(6);
				for (i = 0; i < pala.length(); i++) {
					p = p + 1;
					sopa[x][p] = pala.charAt(i);

					if (i == 0) {
						palabra.setFila(p);
						palabra.setColumna(x);
					}
					if(i == (pala.length() - 1)) {
						palabra.setFilaFin(p);
						palabra.setColumnaFin(x);
					}
				}
			}
			if (b == 2) {
				x = r.nextInt(6);
				for (i = 0; i < pala.length(); i++) {
					p = p + 1;
					sopa[p][x] = pala.charAt(i);

					if (i == 0) {
						palabra.setFila(x);
						palabra.setColumna(p);
					}
					if(i == (pala.length() - 1)) {
						palabra.setFilaFin(x);
						palabra.setColumnaFin(p);
					}
				}
			}
			if (b == 3) {
				x = r.nextInt(6);
				for (i = 0; i < pala.length(); i++) {
					p = p + 1;
					sopa[i][x + p] = pala.charAt(i);

					if (i == 0) {
						palabra.setFila(x + p);
						palabra.setColumna(i);
					}
					if(i == (pala.length() - 1)) {
						palabra.setFilaFin(x +p);
						palabra.setColumnaFin(i);
					}
				}
			}

			palabras.add(palabra);
			
			String identificador = UUID.randomUUID().toString();
			identificador.replaceAll("-", "");
			identificador.substring(0, 32);
		    
			resultado.setIdentificador(identificador);
			resultado.setPalabras(palabras);
			resultado.setSopa(sopa);

		} catch (Exception e) {
			resultado = null;
		} finally {
			return resultado;
		}
	}

	public boolean registrarSopaDeLetra(ResponseCrearSopa sopaDeLetras) {
		
		try {		
			Gson gson = new Gson();
			String json = gson.toJson(sopaDeLetras);
			
			PropertiesConfiguration conf = new PropertiesConfiguration("config.properties");
			conf.setProperty(sopaDeLetras.getIdentificador(), json);
			conf.save();   
            return true;
		}catch(Exception e) {
			System.out.println("ERROR");
			return false;
		}
	}

	public List<String> obtenerListaPalabras(String idSopa) {
		try{
			List<String> resultado = new ArrayList<String>();
			InputStream input = new FileInputStream("config.properties");
            
			Properties prop = new Properties();
            prop.load(input);

            Gson gson = new Gson();
            ResponseCrearSopa sopaDeLetra = gson.fromJson(prop.getProperty(idSopa), ResponseCrearSopa.class);
            
            List<PalabrasAgregadas> palabras = new ArrayList<PalabrasAgregadas>();
            
            palabras = sopaDeLetra.getPalabras();
            
            for (PalabrasAgregadas palabra : palabras) {
            	resultado.add(palabra.getPalabra());
			}
            
            
            return resultado;

        } catch (Exception ex) {
        	return null;
        }
		
	}

	public char[][] visualizarSopa(String idSopa) {
		try{
			InputStream input = new FileInputStream("config.properties");
            
			Properties prop = new Properties();
            prop.load(input);

            Gson gson = new Gson();
            ResponseCrearSopa sopaDeLetra = gson.fromJson(prop.getProperty(idSopa), ResponseCrearSopa.class);
            
            char[][] sopa = sopaDeLetra.getSopa();                        
            
            return sopa;

        } catch (Exception ex) {
        	return null;
        }
	}

	public ResponseCrearSopa obtenerSopa(String idSopa) {
		try{
			InputStream input = new FileInputStream("config.properties");
            
			Properties prop = new Properties();
            prop.load(input);

            Gson gson = new Gson();
            ResponseCrearSopa sopaDeLetra = gson.fromJson(prop.getProperty(idSopa), ResponseCrearSopa.class);

            return sopaDeLetra;

        } catch (Exception ex) {
        	return null;
        }
	}

	public void actualizarSopa(String identificador, PalabrasAgregadas pal) {
		ResponseCrearSopa sopaLetra = obtenerSopa(identificador);
		char [][] sopa = sopaLetra.getSopa();		
		
		try{
			if (pal.getOrientacion() == 0) {
				int i, x, p;
				p = pal.getColumna();
				x = pal.getFila();
				for (i = 0; i < pal.getPalabra().length(); i++) {					
					sopa[x + p][i] = Character.toUpperCase(pal.getPalabra().charAt(i));
					p = p + 1;
				}
			}
			
			if (pal.getOrientacion() == 1) {
				int i, x, p;
				p = pal.getColumna();
				x = pal.getFila();
				for (i = 0; i < pal.getPalabra().length(); i++) {					
					sopa[x][p] = Character.toUpperCase(pal.getPalabra().charAt(i));
					System.out.println(sopa[x][p]);
					p = p + 1;
				}
			}
			
			if (pal.getOrientacion() == 2) {
				int i, x, p;
				p = pal.getColumna();
				x = pal.getFila();
				for (i = 0; i < pal.getPalabra().length(); i++) {				
					sopa[p][x] = Character.toUpperCase(pal.getPalabra().charAt(i));				
					p = p + 1;
				}						
			}
			
			if (pal.getOrientacion()  == 3) {
				int i, x, p;
				p = pal.getColumna();
				x = pal.getFila();
				for (i = 0; i < pal.getPalabra().length(); i++) {					
					sopa[i][x + p] = Character.toUpperCase(pal.getPalabra().charAt(i));	
					p = p + 1;
				}
			}
			
			sopaLetra.setSopa(sopa);		
					
			Gson gson = new Gson();
			String json = gson.toJson(sopaLetra);
			
			PropertiesConfiguration conf = new PropertiesConfiguration("config.properties");
			conf.setProperty(sopaLetra.getIdentificador(), json);
			conf.save();
			
			/*
			for (int i = 0; i < sopa.length; i++) {
				for (int j = 0; j < sopa.length; j++)
					System.out.print(sopa[j][i] + "  ");
				System.out.println();
			}*/
		} catch (Exception ex) {}
	}


}
