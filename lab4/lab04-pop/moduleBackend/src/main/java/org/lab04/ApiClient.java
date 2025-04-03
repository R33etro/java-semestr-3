package org.lab04;

import java.net.http.*;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class ApiClient {
    private static final String BASE_URL = "https://api.nfz.gov.pl/app-itl-api/queues?page1&format=json&api-version=1.3";

    public static String fetchQueues(String locality, String Case, String Type) {
        try {
            String province = "";
            HttpClient client = HttpClient.newHttpClient();
            if (Objects.equals(locality, "Wrocław")) {
                province = "01";
            } else if(Objects.equals(locality,"Kraków")){
                province = "06";
            }
            HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(BASE_URL + "&province=" + province + "&locality=" + locality + "&case=" + Case + "&benefit=" + Type))
                        .GET()
                        .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return String.valueOf(Optional.of(response.body()));
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(Optional.empty());
        }
    }
}
