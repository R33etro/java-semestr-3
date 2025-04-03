package org.lab04;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static List<QueueEntry> parse(String jsonString)  {
        List<QueueEntry> entries = new ArrayList<>();

//        String jsonString = String.valueOf(ApiClient.fetchQueues("Wrocław","01","Stomatologiczna"));
//        System.out.println(jsonString);
        jsonString = jsonString.substring(9, jsonString.length() - 1);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode dataArray = rootNode.get("data");

            if (dataArray != null && dataArray.isArray()) {
                // Loop through each item in the 'data' array
                for (JsonNode item : dataArray) {
                    // Extract the 'attributes' field
                    JsonNode attributes = item.get("attributes");
                    String provider = String.valueOf(attributes.get("provider"));
                    String address = String.valueOf(attributes.get("address"));
                    String locality = String.valueOf(attributes.get("locality"));
                    String phone = String.valueOf(attributes.get("phone"));
                    // Extract the 'dates' field
                    JsonNode dates = attributes.get("dates");
                    String date = String.valueOf(dates.get("date"));
                    // Create entry object and add it to list
                    QueueEntry entry = new QueueEntry(provider,address,locality,phone,date);
                    entries.add(entry);
                }
            } else {
                System.out.println("No 'data' array found in the JSON.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading or parsing the JSON file.");
        }
//        System.out.println(entries);
        return entries;
    }
}
