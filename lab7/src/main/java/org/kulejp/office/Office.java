package org.kulejp.office;

import java.rmi.RemoteException;
import java.util.ArrayList;

import interfaces.IHouse;
import interfaces.IOffice;
import interfaces.ISewagePlant;
import interfaces.ITanker;


public class Office implements IOffice {

    private final ISewagePlant iSewagePlant;
    private int nextTankerNumber = 1;
    private ArrayList<TankerDetails> tankers;

    ////-------------Klasa pomocnicza---------------
    private static class TankerDetails {
        private String name;
        private int number;
        private boolean isReady;
        private ITanker iTanker;
        TankerDetails(int number, String name, ITanker iTanker, boolean isReady) {
            this.number = number;
            this.name = name;
            this.iTanker = iTanker;
            this.isReady = isReady;
        }
        public void setReady(boolean b) {
            this.isReady = true;
        }
        public int getNumber() {
            return number;
        }

        public ITanker getITanker() {
            return iTanker;
        }

        public boolean isReady() {
            return isReady;
        }
    }
////-------------Klasa pomocnicza---------------


    public Office(ISewagePlant iSewagePlant) {
        this.iSewagePlant = iSewagePlant;
        this.tankers = new ArrayList<>();
    }


    public void sendRequestGETSTATUS(int tankerNumber) throws RemoteException {
            int vol = iSewagePlant.getStatus(tankerNumber);
            System.out.println("OFFICE: status for tanker " + tankerNumber + ": " + vol);

    }

    public void sendRequestSETPAYOFF(int tankerNumber) throws RemoteException {
       iSewagePlant.setPayoff(tankerNumber);
        System.out.println("OFFICE: setting payoff for tanker " + tankerNumber );

    }


    @Override
    public int register(ITanker tanker, String name) throws RemoteException {
        tankers.add(new TankerDetails(nextTankerNumber,name,tanker, true));
        System.out.println("OFFICE: tanker " + nextTankerNumber + " registered");
        return nextTankerNumber++;
    }


    @Override
    public int order(IHouse house, String name) throws RemoteException {
        //find available tanker
        for (TankerDetails tanker : tankers) {
            //send tanker on a job if it's ready
            if (tanker.isReady()) {
                //set its state to not ready
                tanker.setReady(false);
                tanker.getITanker().setJob(house);

                //return 1 if successful
                System.out.println("OFFICE: Tanker " + tanker.getNumber() + " going to work");
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void setReadyToServe(int tankerNumber) {
        for (TankerDetails tanker : tankers) {
            if (tanker.getNumber() == tankerNumber) {
                tanker.setReady(true);
                System.out.println("OFFICE: Tanker " + tankerNumber + " is ready");
                break;
            }
        }
    }


//    public static void main(String[] args) {
//        Office office = new Office("localhost",6000, "localhost",7000);
//        office.startServer();
//
//    }
}
