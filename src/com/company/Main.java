package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

/**
 * Documentacion oficial de google
 * https://developers.google.com/custom-search/v1/overview
 */
public class Main {

    private static String endPoint = "https://www.googleapis.com/customsearch/v1?";

    public static void main(String[] args) throws Exception {

        String apiKey = null, cx = null;

        // Leer archivo de configuracion.
        try {
            JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader("config.json"));
            apiKey = json.get("key").getAsString();
            cx = json.get("cx").getAsString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if ((apiKey == null) || (cx == null)) {
            throw new IllegalArgumentException("El archivo de configuracion no contiene valores validos.");
        }


        // Ingresar objetivo.
        Scanner in = new Scanner(System.in);
        String target = in.nextLine();
        String targetFormatted = target.replaceAll(" ", "+");

        targetFormatted = "site:linkedin.com" + "+" + targetFormatted;

        // Creamos la url de ejemplo.
        String queryString = endPoint + "key=" + apiKey +
                "&cx=" + cx + "&q=" + targetFormatted + "&num=10&start=1";

        System.out.println(queryString);

        // Generamos el objeto de la conexion.
        URL url = new URL(queryString);

        // Leemos el resultado del request.
        Scanner scanner = new Scanner(url.openStream());
        String response = scanner.useDelimiter("\\Z").next();

        // Parsear el objeto json con Gson.
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response, JsonObject.class);
        Resultado[] resultados = gson.fromJson(json.get("items"), Resultado[].class);

        // Crear el objeto para escribir el archivo.
        FileWriter fileWriter = new FileWriter("resultado.csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        // Escribimos en el archivo TXT
        for (Resultado resultado : resultados) {
            System.out.println(resultado);
            printWriter.println(String.format("\"%s\", \"%s\"", resultado.title, resultado.link));
        }
        // Cerrar el buffer y escribir.
        printWriter.close();
        // Cerrar el buffer de la conexion http.
        scanner.close();


    }
}
