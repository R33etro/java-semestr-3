package org.kulejp.office;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Office implements IOffice{
    private int port;
    private String host;
    private int sewagePlantPort;
    private String sewagePlantHost;
    private ServerSocket serverSocket;

    private Map<Integer, TankerDetails> tankers = new ConcurrentHashMap<>();
    private int nextTankerNumber = 1;

    private static class TankerDetails
    {
        String host;
        int port;
        boolean isReady;

        TankerDetails(String host, int port)
        {
            this.host = host;
            this.port = port;
            this.isReady = false;
        }
    }

    public Office(String host, int port, String sewagePlantHost, int sewagePlantPort) {
        this.port = port;
        this.host = host;
        this.sewagePlantPort = sewagePlantPort;
        this.sewagePlantHost = sewagePlantHost;
    }

    public void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Office server running on port " + port);
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
            if (request != null && request.startsWith("r:"))
            {
                //r:127.0.0.1,4001
                int index = request.indexOf(",");
                String tankerHost = request.substring(2,index);
                String tankerPort = request.substring(index+1);
                out.println(register(tankerHost, tankerPort));
            } else if (request != null && request.startsWith("o:"))
            {
                //o:127.0.0.1,5001
                int index = request.indexOf(",");
                String houseHost = request.substring(2,index);
                String housePort = request.substring(index+1);
                out.println(order(houseHost, Integer.parseInt(housePort)));
            } else if (request != null && request.startsWith("sr:"))
            {
                //sr:14
                int tankerNumber = Integer.parseInt(request.substring(3));
                setReadyToServe(tankerNumber);
                out.println("confirmed" + tankerNumber);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    // send request ...
    public void sendRequestSETJOB(String houseHost, int housePort, String tankerHost, int tankerPort) {
        try (Socket socket = new Socket(tankerHost, tankerPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String request = "sj:" + houseHost + "," + housePort;
            out.println(request);
//            String response = in.readLine();
//            System.out.println(request);
//            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendRequestGETSTATUS(String tankerNumber) {
        try (Socket socket = new Socket(sewagePlantHost, sewagePlantPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String request = "gs:" + tankerNumber;
            out.println(request);
            String response = in.readLine();
            System.out.println("OFFICE: response - get status for tanker " + tankerNumber + " = "+ response);
//            sendRequestSETPAYOFF(tankerNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendRequestSETPAYOFF(String tankerNumber) {
        try (Socket socket = new Socket(sewagePlantHost, sewagePlantPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request = "spo:" + tankerNumber;
            out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int register(String tankerHost, String tankerPort) {

        int tankerNumber = nextTankerNumber-1;
        TankerDetails details = new TankerDetails(tankerHost, Integer.parseInt(tankerPort));
        tankers.put(tankerNumber, details);
        System.out.println("OFFICE: tanker " + tankerNumber + " registered");
        nextTankerNumber++;
        return tankerNumber;
    }


    @Override
    public int order(String houseHost, int housePort) {

        if(!tankers.isEmpty()){
            Random random = new Random();

            int tankerNumber = random.nextInt(tankers.size());
            String tankerHost = tankers.get(tankerNumber).host;
            int tankerPort = tankers.get(tankerNumber).port;
            System.out.println(tankerHost + ":" + tankerPort);

            sendRequestSETJOB(houseHost, housePort, tankerHost, tankerPort);
            return 1;
        }
        return 0;
    }

    @Override
    public void setReadyToServe(int tankerNumber) {
        if(!tankers.containsKey(tankerNumber)){

            String tankerHost = tankers.get(tankerNumber).host;
            int tankerPort = tankers.get(tankerNumber).port;
            tankers.get(tankerNumber).isReady = true;
            System.out.println("OFFICE: " + tankerNumber + " " + tankerHost + " " + tankerPort + " ready to serve");
        }
    }


//    public static void main(String[] args) {
//        Office office = new Office("localhost",6000, "localhost",7000);
//        office.startServer();
//
//    }
}
