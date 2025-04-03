package org.example;

import java.util.Comparator;

public class BucketsComparator implements Comparator<Bucket> {
    @Override
    public int compare(Bucket a, Bucket b) {
        return a.getAngle() - b.getAngle();
    }
}
