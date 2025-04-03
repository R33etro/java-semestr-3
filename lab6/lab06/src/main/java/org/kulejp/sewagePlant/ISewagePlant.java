package org.kulejp.sewagePlant;

public interface ISewagePlant {
    void setPumpIn(int tankerNumber, int volume);
    int getStatus(int tankerNumber);
    void setPayoff(int tankerNumber);
}
