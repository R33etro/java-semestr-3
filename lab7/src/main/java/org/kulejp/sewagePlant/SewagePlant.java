package org.kulejp.sewagePlant;

import java.rmi.RemoteException;
import java.util.ArrayList;

import interfaces.ISewagePlant;


public class SewagePlant implements ISewagePlant {

    ArrayList<TankerData> tankerData = new ArrayList<>();

    public SewagePlant() throws RemoteException {

    }

    @Override
    public void setPumpIn(int number, int volume) {
//        updateTankerData(number, volume);
        tankerData.add(new TankerData(number, volume));
        System.out.println("SEWAGE PLANT: Tanker " + number + " pumped in " + volume);
    }

    @Override
    public int getStatus(int number) {
        for (TankerData tankersData : tankerData) {
            if (tankersData.getNumber() == number) {
                System.out.println("SEWAGE PLANT: Getting status for tanker " + number);
                return tankersData.getAllVol();
            }
        }
        return 0;
    }

    @Override
    public void setPayoff(int number) {
        System.out.println("SEWAGE PLANT: Setting payoff for tanker " + number);
        updateTankerData(number, 0);
    }

//    public static void main(String[] args) {
//    }
////------------------------------------------------------------------------------
    public class TankerData {
        private int number;
        private int allVol;

        public TankerData(int number, int allVol) {
            this.number = number;
            this.allVol = allVol;
        }

        public int getNumber() {
            return number;
        }

        public int getAllVol() {
            return allVol;
        }

        public void setAllVol(int allVol) {
            this.allVol = allVol;
        }
    }

    private synchronized void updateTankerData(int number, int volume) {
        for (TankerData tankersData : tankerData) {
            if (tankersData.getNumber() == number) {
                tankersData.setAllVol(tankersData.getAllVol() + volume);
                return;
            }
        }
    }
}
