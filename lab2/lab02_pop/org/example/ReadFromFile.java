package org.example;

import java.io.*;
import java.util.ArrayList;

public class ReadFromFile {
    public static void readBucketsFromFile(String path, ArrayList<Bucket> list_of_something) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                int angle = Integer.parseInt(data[1].trim());
                int volume = Integer.parseInt(data[2].trim());
                Bucket bucket = new Bucket(id, angle, volume);
                list_of_something.add(bucket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readPlacesFromFile(String path, ArrayList<Place> list_of_something) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                int radius = Integer.parseInt(data[1].trim());
                Place place = new Place(id, radius);
                list_of_something.add(place);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
