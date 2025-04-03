package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CalculateCompetition {
    public static void calculateCompetition(double w1, double w2, ArrayList<Bucket> list_of_buckets, ArrayList<Place> list_of_places) {
        double summary_height = 0;
        double remaining_sand = 0;
        int portion_of_sand = 1;

        ArrayList<Results> list_of_results = new ArrayList<>();

        for (Bucket bucket : list_of_buckets) {
            remaining_sand += bucket.getVolume();
        }

        //sortujemy wiaderka z piaskiem od najmniejszego kąta
//        list_of_buckets.sort(new BucketsComparator());
        //lub wyrazeniem lambda
        Collections.sort(list_of_buckets, (b1, b2) -> b1.getAngle() - b2.getAngle());

        //wysypujemy piasek na kazde pole rownoczesnie od piasku z najmniejszym katem

        int current_bucket = 0;
        while ((remaining_sand * w1) > 0) { //pozostały piasek * w1 żeby wykorzystać optymalną ilość do usypania wież
            for (Place listOfPlace : list_of_places) {
                if (!(list_of_buckets.get(current_bucket).getVolume() > 0)) {current_bucket++;}
                if (!(remaining_sand * w1 > 0)) {break;}
                int an = list_of_buckets.get(current_bucket).getAngle();
                double r1 = listOfPlace.getRadius();
                double r2 = CalculateLayer.calculateRadius(r1, an);
                listOfPlace.setRadius(r2);
                if (r2 > 0.0) {
                    double height = CalculateLayer.calculateHeight(r1, r2, an);
                    summary_height += height;
                    Results result = new Results(listOfPlace.getId(), an, height);
                    list_of_results.add(result);
//                    System.out.println("wiaderko=" + current_bucket + " kąt= " + an + " wysokosc=" + summary_height + " piasek=" + remaining_sand);
                    remaining_sand -= portion_of_sand;
                    double rem_sand_in_bucket = list_of_buckets.get(current_bucket).getVolume();
                    list_of_buckets.get(current_bucket).setVolume(rem_sand_in_bucket - portion_of_sand);
                } else {
                    double height = CalculateLayer.calculateHeightOfTriangle(r1, an);
                    summary_height += CalculateLayer.calculateHeightOfTriangle(r1, an);
                    Results result = new Results(listOfPlace.getId(), an, height);
                    list_of_results.add(result);
//                    System.out.println("wiaderko=" + current_bucket + " kąt= " + an + " wysokosc=" + summary_height + " piasek=" + remaining_sand);
                    remaining_sand -= portion_of_sand;
                    double rem_sand_in_bucket = list_of_buckets.get(current_bucket).getVolume();
                    list_of_buckets.get(current_bucket).setVolume(rem_sand_in_bucket - portion_of_sand);
                    break;
                }
            }
        }
//        list_of_results.sort(new ResultComparator());
//        Collections.sort(list_of_results, (p1, p2) -> p1.getPlace() - p2.getPlace());
        Collections.sort(list_of_results, Comparator.comparingInt(Results::getPlace));
        System.out.println(list_of_results);

        double result = (summary_height / list_of_places.size()) * w1 + remaining_sand * w2;
        System.out.println("result = " + result);

        WriteResultsToFile.writeResutlsToFile(list_of_results,w1,w2,result);
    }
}
