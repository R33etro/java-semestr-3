package org.example;

import java.util.ArrayList;
import java.util.Random;

public class CalculateCompetitionIDK {
    public static void calculateCompetition(double w1, double w2, ArrayList<Bucket> list_of_buckets, ArrayList<Place> list_of_places) {
        double summary_height = 0;
        double remaining_sand = 0;
        double best_result = 0;
        int portion_of_sand = 1;
        ArrayList<Results> list_of_results = new ArrayList<>();

        int number_of_buckets = list_of_buckets.size();
        for (Bucket bucket : list_of_buckets) {
            remaining_sand += bucket.getVolume();
        }

        for(int i = 0 ; i < 1000 ; i++) {
            Random rand = new Random();
            for (Place listOfPlace : list_of_places) {
                double r1 = listOfPlace.getRadius();
                while (r1 > 0 && (remaining_sand * w1) > 0) {
                    int random = rand.nextInt(number_of_buckets);
                    if (list_of_buckets.get(random).getVolume() > 0) {
                        int an = list_of_buckets.get(random).getAngle();
                        double r2 = CalculateLayer.calculateRadius(r1, an);
                        if (r2 > 0.0) {
                            double height = CalculateLayer.calculateHeight(r1, r2, an);
                            summary_height += height;
                            Results result = new Results(listOfPlace.getId(), an, height);
                            list_of_results.add(result);
                            System.out.println("Fun trapez : promien dolny=" + r1 + " promien gorny=" + r2 + " kąt= " + an + " wysokosc=" + summary_height + " piasek=" + remaining_sand);
                            r1 = r2;
                            remaining_sand -= portion_of_sand;
                            double rem_sand_in_bucket = list_of_buckets.get(random).getVolume();
                            list_of_buckets.get(random).setVolume(rem_sand_in_bucket - portion_of_sand);
                        } else {
                            double height = CalculateLayer.calculateHeightOfTriangle(r1, an);
                            summary_height += CalculateLayer.calculateHeightOfTriangle(r1, an);
                            Results result = new Results(listOfPlace.getId(), an, height);
                            list_of_results.add(result);
                            System.out.println("Fun. trojkat : promien dolny=" + r1 + " promien gorny=" + r2 + " kąt= " + an + " wysokosc=" + summary_height + " piasek=" + remaining_sand);
                            remaining_sand -= portion_of_sand;
                            double rem_sand_in_bucket = list_of_buckets.get(random).getVolume();
                            list_of_buckets.get(random).setVolume(rem_sand_in_bucket - portion_of_sand);
                            break;
                        }
                    }
                }
            }
            double result = (summary_height / list_of_places.size()) * w1 + remaining_sand * w2;
            System.out.println("aktualny wynik " + result);
            if (result > best_result) {
                best_result = result;
            }
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("best result = " + best_result);
        WriteResultsToFile.writeResutlsToFile(list_of_results,w1,w2,best_result);

    }
}
