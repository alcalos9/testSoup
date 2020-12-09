package com.
testSoup.testSoup.controller;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.testSoup.testSoup.model.response.ResCrearSopa;
import com.testSoup.testSoup.model.response.ResEncontrarPalabra;
import com.testSoup.testSoup.model.resquest.ReqCrearSopa;
import com.testSoup.testSoup.model.resquest.ReqEncontrarPalabra;
import com.testSoup.testSoup.model.soup.PalabrasAgregadas;
import com.testSoup.testSoup.model.soup.ResponseCrearSopa;
import com.testSoup.testSoup.services.TestSoupServices;

@RestController
public class TestSoupController {

	private TestSoupServices servicio = new TestSoupServices();
	
	@RequestMapping(value = "/getAll", method= RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public String getUrls(){
		
		return "Ok";
	}

	@RequestMapping(value = "/alphabetSoup", method= RequestMethod.POST)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResCrearSopa crearSopa(@RequestBody ReqCrearSopa request){
		
		ResCrearSopa resCrearSopa = new ResCrearSopa();
		
		System.out.println(request.toString());
		
		try {
			
			ResponseCrearSopa sopaDeLetras = new ResponseCrearSopa();
			
			sopaDeLetras = servicio.crearSopa(request);
			boolean res = servicio.registrarSopaDeLetra(sopaDeLetras);
			
			
			/**********  Visualizar sopa de letras **********/
			System.out.println("-->"+sopaDeLetras.getIdentificador());
			
			for (PalabrasAgregadas palabra : sopaDeLetras.getPalabras()) {
				System.out.println("-->"+palabra.toString());
			}
			
			char[][] sopa = sopaDeLetras.getSopa();
			
			for (int i = 0; i < request.getW(); i++) {
				for (int j = 0; j < request.getH(); j++)
					System.out.print(sopa[j][i] + "  ");
				System.out.println();
			}
			/**********  FIN **********/

			if(res) {
				resCrearSopa.setId(sopaDeLetras.getIdentificador());
				return resCrearSopa;
			}else {
				resCrearSopa.setId("Error al crear la sopa de letras");			
				return resCrearSopa;
			}
			
					
		}catch(Exception e) {
			resCrearSopa.setId("Error al crear la sopa de letras");			
			return resCrearSopa;
		}
	}	
	
	@RequestMapping(value = "/alphabetSoup/list/{idSopa}", method= RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")	
	public List<String> obtenerListaPalabras(@PathVariable("idSopa") String idSopa){
		
		try {			
			System.out.println(idSopa);			
			
			List<String> palabras = new ArrayList<String>();
			palabras.add("primero");
			palabras.add("dos");
			
			return palabras;			
		}catch(Exception e) {						
			return null;
		}
		
	}
		
	@RequestMapping(value = "/alphabetSoup/view/{idSopa}", method= RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")	
	public List<String> visualizarSopa(@PathVariable("idSopa") String idSopa){
		
		try {
			System.out.println(idSopa);
			
			List<String> palabras = new ArrayList<String>();
			palabras.add("primero");
			palabras.add("dos");
			
			return palabras;
		}catch(Exception e) {						
			return null;
		}
		
	}
	
	@RequestMapping(value = "/alphabetSoup/{idSopa}", method= RequestMethod.PUT)
	@CrossOrigin(origins = "*", allowedHeaders = "*")	
	public ResEncontrarPalabra encontrarPalabra(@RequestBody ReqEncontrarPalabra request, @PathVariable("idSopa") String idSopa){
		
		ResEncontrarPalabra resultado = new ResEncontrarPalabra();
		
		try {
			System.out.println(idSopa);
			System.out.println(request.toString());
			
			
			resultado.setResultado("Correcto");
			
			return resultado;
		}catch(Exception e) {
			resultado.setResultado("Error al encontrar la palabra");;			
			return resultado;
		}
		
	}
	

	
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
