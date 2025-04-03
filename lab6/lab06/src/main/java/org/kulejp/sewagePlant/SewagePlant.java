package org.kulejp.sewagePlant;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class SewagePlant implements ISewagePlant {
    private String host;
    private int port;

    private ServerSocket serverSocket;

    ArrayList<TankerData> tankerData = new ArrayList<>();


    public class TankerData {
        private int id;
        private int totalSeawge = 0;
        public TankerData(int id, int totalSeawge) {this.id = id; this.totalSeawge = totalSeawge;}
        public int getId() { return id; }
        public int getTotalSeawge() { return totalSeawge; }
        public void setId(int id) { this.id = id; }
        public void setTotalSeawge(int totalSeawge) { this.totalSeawge = totalSeawge; }
    }

    public SewagePlant(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Sewage server running on port " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // handle request ...
    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request != null && request.startsWith("spi:"))
            {
                //spi:14,50
                int index = request.indexOf(",");
                String tankerNumber = request.substring(4,index);
                String vol = request.substring(index+1);
                setPumpIn(Integer.parseInt(tankerNumber),Integer.parseInt(vol));
            } else if (request != null && request.startsWith("gs:"))
            {
                //gs:14
                String tankerNumber = request.substring(3);
                out.println(getStatus(Integer.parseInt(tankerNumber)));
            } else if (request != null && request.startsWith("spo:"))
            {
                //spo:14
                int tankerNumber = Integer.parseInt(request.substring(4));
                setPayoff(tankerNumber);

            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPumpIn(int number, int volume) {
        updateTankerData(number, volume);
        System.out.println("SEWAGE PLANT: Tanker " + number + " pumped in " + volume);
    }

    @Override
    public int getStatus(int number) {
        for (TankerData tankersData : tankerData) {
            if (tankersData.getId() == number) {
                System.out.println("SEWAGE PLANT: Getting status for tanker " + number);
                return tankersData.getTotalSeawge();
            }
        }
        return 0;
    }

    @Override
    public void setPayoff(int number) {
        System.out.println("SEWAGE PLANT: Setting payoff for tanker " + number);
        updateTankerData(number, 0);
    }
    private synchronized void updateTankerData(int number, int volume) {
        for (TankerData tankersData : tankerData) {
            if (tankersData.getId() == number) {
                tankersData.setTotalSeawge(tankersData.getTotalSeawge() + volume);
                return;
            }
        }
    }

//    public static void main(String[] args) {
//        SewagePlant sp = new SewagePlant("localhost",7000);
//        sp.startServer();
//    }
}
