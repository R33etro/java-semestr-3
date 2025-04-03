package org.example;

import java.util.Comparator;

public class ResultComparator implements Comparator<Results> {
    @Override
    public int compare(Results a, Results b) {
        return a.getPlace() - b.getPlace();
    }
}
