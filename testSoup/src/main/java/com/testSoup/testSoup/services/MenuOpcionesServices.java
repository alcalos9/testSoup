package com.testSoup.testSoup.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.testSoup.testSoup.model.response.ResCrearSopa;



public class MenuOpcionesServices {

	public boolean crearSopa(RestTemplate restTemplate) {
		URL url;
		HttpURLConnection conn = null;

		try {			
			url = new URL("http://localhost:8080/alphabetSoup");
		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			
			JSONObject cred = new JSONObject();
			
			cred.put("w","15");
			cred.put("h","15");
			cred.put("ltr",true);
			cred.put("rtl",true);
			cred.put("ttb",true);
			cred.put("btt",true);
			cred.put("d",true);
			
			OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
			wr.write(cred.toString());
			wr.flush();
			wr.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			
			StringBuilder response = new StringBuilder();
			String responseSingle = null; 
            while ((responseSingle = br.readLine()) != null)  
            { 
                response.append(responseSingle); 
            } 
            String resultado = response.toString();
            
            Gson gson = new Gson();  
            ResCrearSopa id = gson.fromJson(resultado, ResCrearSopa.class);
            System.out.println(" -> el identificar de la sopa es: "+ id.getId());
   

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			conn.disconnect();
		}		

		return true;
	}

	public List<String> obtenerLetra(RestTemplate restTemplate, String identificador) {

		List<String> result = new ArrayList<String>();
		URL url;
		HttpURLConnection conn = null;

		try {			
			url = new URL("http://localhost:8080/alphabetSoup/list/"+identificador);
		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/json; charset=utf-8");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			
			StringBuilder response = new StringBuilder();
			String responseSingle = null; 
            while ((responseSingle = br.readLine()) != null)  
            { 
                response.append(responseSingle); 
            } 
            String resultado = response.toString();
            
            Gson gson = new Gson();  
            String[] founderArray = gson.fromJson(resultado, String[].class);
            
            for (String string : founderArray) {
            	result.add(string);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			conn.disconnect();
		}		

		return result;
	}

	public char[][] obtenerSopa(RestTemplate restTemplate, String identificador) {
		char[][] result;
		URL url;
		HttpURLConnection conn = null;

		try {			
			url = new URL("http://localhost:8080/alphabetSoup/view/"+identificador);
		
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("content-type", "application/json; charset=utf-8");
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			
			StringBuilder response = new StringBuilder();
			String responseSingle = null; 
            while ((responseSingle = br.readLine()) != null)  
            { 
                response.append(responseSingle); 
            } 
            String resultado = response.toString();
            
            Gson gson = new Gson();  
            String[] founder = gson.fromJson(resultado, String[].class);
            
            result = new char[founder.length][founder[0].length()];
            
            int i, j;
            for (i = 0; i < founder.length; i++)
				for (j = 0; j < founder[0].length(); j++) {
					result[i][j] = founder[i].charAt(j);
				}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			conn.disconnect();
		}		

		return result;
	}

}
