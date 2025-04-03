package org.kulejp.house;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IHouse;
import interfaces.IOffice;


public class House implements IHouse {

    int maxVolume;
    int currentVolume;

    private final IOffice ioffice;
    private IHouse ihouse;
    private String name;

    public House(IOffice iOffice, String name,  int maxVolume) throws RemoteException {
        this.ioffice = iOffice;
        this.name = name;
        this.maxVolume = maxVolume;
        this.ihouse = this;
    }


    // send request to Office
    public void sendRequestORDER() throws RemoteException {
        ioffice.order(ihouse, name);
        System.out.println("sending order");
    }


    @Override
    public int getPumpOut(int maxVolOfTanker) {
        System.out.println("HOUSE: volume before: " + this.currentVolume);

        if (this.currentVolume >= maxVolOfTanker) {
            this.currentVolume -= maxVolOfTanker;
            System.out.println("HOUSE: volume after: " + this.currentVolume);
            return maxVolOfTanker;
        } else {
            int temp = this.currentVolume;
            this.currentVolume = 0;
            System.out.println("HOUSE: volume after: " + this.currentVolume);
            return temp;
        }
    }

}
