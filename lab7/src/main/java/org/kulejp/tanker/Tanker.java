package org.kulejp.tanker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

import interfaces.IHouse;
import interfaces.ISewagePlant;
import interfaces.IOffice;
import interfaces.ITanker;

public class Tanker implements ITanker {

    private int number;
    private final int maxVol;
    private int allVolume = 0;

    private final ISewagePlant iSewagePlant;
    private final IOffice iOffice;
    private String name;

    public Tanker(int maxVol, ISewagePlant iSewagePlant, IOffice iOffice, String name) {
        this.maxVol = maxVol;
        this.iSewagePlant = iSewagePlant;
        this.iOffice = iOffice;
        this.name = name;
    }
    public void registerTanker(ITanker iTanker, String name) {
        try {
            this.name = String.valueOf(iOffice.register((ITanker) iTanker, name));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Registered: " + name);
    }


    @Override
    public void setJob(IHouse house) throws RemoteException{
        iOffice.setReadyToServe(number);
        System.out.println("TANKER: " + name + " is ready");
        allVolume += house.getPumpOut(maxVol);
        System.out.println("TANKER: pumping out");
        iSewagePlant.setPumpIn(number, allVolume);
        System.out.println("TANKER: pumping in to sewage plant");
        this.allVolume = 0;
    }


//    public static void main(String[] args) {
//    }
}
