package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeRateService {

    private static final String API_KEY = "5459da98b18692cfc9a99e91";
    private static final String URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public Map<String, Double> buscarMoedasFiltradas() {
        try {
            String json = buscarJsonDaApi();
            return filtrarMoedas(json);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar moedas da API", e);
        }
    }

    private String buscarJsonDaApi() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private Map<String, Double> filtrarMoedas(String json) {

        Map<String, Double> moedas = new HashMap<>();
        List<String> permitidas = List.of("ARS", "BRL", "CNY", "EUR", "GBP", "USD");

        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject rates = root.getAsJsonObject("conversion_rates");

        for (String codigo : permitidas) {
            if (rates.has(codigo)) {
                moedas.put(codigo, rates.get(codigo).getAsDouble());
            }
        }

        return moedas;
    }
}


