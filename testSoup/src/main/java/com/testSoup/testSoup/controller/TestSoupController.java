package com.testSoup.testSoup.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.testSoup.testSoup.services.MenuOpcionesServices;
import com.testSoup.testSoup.services.TestSoupServices;

@RestController
public class TestSoupController {

	private TestSoupServices servicio = new TestSoupServices();
	private MenuOpcionesServices servicioMenu = new MenuOpcionesServices();

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public String getUrls() {

		return "Ok";
	}

	@RequestMapping(value = "/alphabetSoup", method = RequestMethod.POST)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResCrearSopa crearSopa(@RequestBody ReqCrearSopa request) {

		ResCrearSopa resCrearSopa = new ResCrearSopa();

		System.out.println(request.toString());

		try {

			ResponseCrearSopa sopaDeLetras = new ResponseCrearSopa();

			sopaDeLetras = servicio.crearSopa(request);
			boolean res = servicio.registrarSopaDeLetra(sopaDeLetras);

			/**********
			 * Visualizar sopa de letras **********
			 * System.out.println("-->"+sopaDeLetras.getIdentificador());
			 * 
			 * for (PalabrasAgregadas palabra : sopaDeLetras.getPalabras()) {
			 * System.out.println("-->"+palabra.toString()); }
			 * 
			 * char[][] sopa = sopaDeLetras.getSopa();
			 * 
			 * for (int i = 0; i < request.getW(); i++) { for (int j = 0; j <
			 * request.getH(); j++) System.out.print(sopa[j][i] + " ");
			 * System.out.println(); } FIN
			 **********/

			if (res) {
				resCrearSopa.setId(sopaDeLetras.getIdentificador());
				return resCrearSopa;
			} else {
				resCrearSopa.setId("Error al crear la sopa de letras");
				return resCrearSopa;
			}

		} catch (Exception e) {
			resCrearSopa.setId("Error al crear la sopa de letras");
			return resCrearSopa;
		}
	}

	@RequestMapping(value = "/alphabetSoup/list/{idSopa}", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<String> obtenerListaPalabras(@PathVariable("idSopa") String idSopa) {

		try {
			List<String> palabras = new ArrayList<String>();
			palabras = servicio.obtenerListaPalabras(idSopa);

			return palabras;
		} catch (Exception e) {
			return null;
		}

	}

	@RequestMapping(value = "/alphabetSoup/view/{idSopa}", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public char[][] visualizarSopa(@PathVariable("idSopa") String idSopa) {

		try {
			char[][] palabras = servicio.visualizarSopa(idSopa);

			/********** Visualizar sopa de letras **********

			for (int i = 0; i < palabras.length; i++) {
				for (int j = 0; j < palabras.length; j++)
					System.out.print(palabras[j][i] + "  ");
				System.out.println();
			}

			********** FIN **********/

			return palabras;
		} catch (Exception e) {
			return null;
		}

	}

	@RequestMapping(value = "/alphabetSoup/{idSopa}", method = RequestMethod.PUT)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResEncontrarPalabra encontrarPalabra(@RequestBody ReqEncontrarPalabra request,
			@PathVariable("idSopa") String idSopa) {

		ResEncontrarPalabra resultado = new ResEncontrarPalabra();

		try {
			System.out.println(idSopa);
			System.out.println(request.toString());

			resultado.setResultado("Correcto");

			return resultado;
		} catch (Exception e) {
			resultado.setResultado("Error al encontrar la palabra");
			;
			return resultado;
		}

	}

	@RequestMapping(value = "/menuOpciones", method = RequestMethod.GET)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public String menuOpciones() {

		ResEncontrarPalabra resultado = new ResEncontrarPalabra();

		try {
			Scanner sn = new Scanner(System.in);
			boolean salir = false;
			int opcion;

			while (!salir) {
				System.out.println();
				System.out.println();
				System.out.println("--> MENÚ <--");
				System.out.println("Digite la opción a realizar:");
				System.out.println("1. Crear sopa de letras");
				System.out.println("2. Ver lista de palabras");
				System.out.println("3. Ver sopa de letras");
				System.out.println("4. Encontrar palabra");
				System.out.println("5. Salir");

				System.out.println("Escribe una de las opciones");
				opcion = sn.nextInt();

				String identificador = "";
				switch (opcion) {
				case 1:
					boolean res = servicioMenu.crearSopa(restTemplate);
					if (res)
						System.out.println(" -> Sopa de letras creada con exito");
					else
						System.out.println(" -> No se pudo crear la sopa de letras");
					break;
				case 2:
					System.out.println(" -> Ingrese el identificador de la sopa de letras:");
					identificador = sn.next();
					List<String> letras = servicioMenu.obtenerLetra(restTemplate, identificador);

					System.out.println(" -> Las palabras de la sopa de letra son:");
					for (String string : letras) {
						System.out.println(string);
					}
					break;
				case 3:
					System.out.println(" -> Ingrese el identificador de la sopa de letras:");
					identificador = sn.next();
					char[][] sopa = servicioMenu.obtenerSopa(restTemplate, identificador);

					System.out.println(" -> La sopa de letra es:");
					for (int i = 0; i < sopa.length; i++) {
						for (int j = 0; j < sopa.length; j++)
							System.out.print(sopa[j][i] + "  ");
						System.out.println();
					}
					break;
				case 4:
					System.out.println("Has seleccionado la opcion 3");
					break;
				case 5:
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 5");
				}

			}

			return "Ok";
		} catch (Exception e) {
			resultado.setResultado("Error al encontrar la palabra");
			;
			return "Error";
		}

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
