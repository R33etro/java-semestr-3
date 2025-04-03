package org.kulejp.office;

public interface IOffice {
    int register(String tankerHost, String tankerPort);
    int order(String houseHost, int housePort);
    void setReadyToServe(int tankerNumber);
}
