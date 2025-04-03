package org.kulejp;

import org.kulejp.house.HousePanel;
import org.kulejp.office.OfficePanel;
import org.kulejp.sewagePlant.SewagePlantPanel;
import org.kulejp.tanker.TankerPanel;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws NotBoundException, RemoteException {

        Tailor.main(args);
        SewagePlantPanel.main(args);
        OfficePanel.main(args);
        HousePanel.main(args);
        TankerPanel.main(args);

    }
}