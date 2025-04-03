package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Bucket> list_of_buckets = new ArrayList<>();
        ArrayList<Place> list_of_places = new ArrayList<>();

        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Suma wag musi byc rowna '1'\n");
        System.out.println("Podaj wage wysokosci wiez");
        double w1 = scanner1.nextDouble();
        System.out.println("Podaj wage pozostalego piasku");
        double w2 = scanner1.nextDouble();

//        String path1 = "src/main/java/org/example/Buckets.txt";
//        String path2 = "src/main/java/org/example/Places.txt";
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Give path to Buckets.txt:");
        String path1 = scanner2.nextLine();
        System.out.println("Give path to Places.txt:");
        String path2 = scanner2.nextLine();

        ReadFromFile.readBucketsFromFile(path1, list_of_buckets);
        ReadFromFile.readPlacesFromFile(path2, list_of_places);

        CalculateCompetition.calculateCompetition(w1, w2, list_of_buckets, list_of_places);
    }
}