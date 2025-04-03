package org.kulejp.tanker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Tanker implements ITanker{
    private int number;
    private final int maxVol;
    private int allVolume = 0;
    private final String host;
    private final int port;
    private final String officeHost;
    private final String officePort;
    private final String sewagePlantHost;
    private final String sewagePlantPort;
    private ServerSocket serverSocket;

    public Tanker(String host, int port, String officeHost, String officePort, String sewagePlantHost, String sewagePlantPort, int maxVol){
        this.maxVol = maxVol;
        this.host = host;
        this.port = port;
        this.officeHost = officeHost;
        this.officePort = officePort;
        this.sewagePlantHost = sewagePlantHost;
        this.sewagePlantPort = sewagePlantPort;
    }

    public void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Tanker server running on port " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleRequest(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // handle request ...
    private void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request != null && request.startsWith("sj:"))
            {
                //sj:127.0.0.1,4001
                int index = request.indexOf(",");
                String houseHost = request.substring(3,index);
                String housePort = request.substring(index+1);
                setJob(houseHost, housePort);
                out.println("ok?");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    // send request ...
    public void sendRequestGETPUMPOUT(String houseHost, String housePort) {
        try (Socket socket = new Socket(houseHost, Integer.parseInt(housePort));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String request = "gp:" + this.maxVol;
            out.println(request);
            String response = in.readLine();
            this.allVolume += Integer.parseInt(response);
//            System.out.println("Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendRequestREGISTER(String officeHost, String officePort) {
        try (Socket socket = new Socket(officeHost, Integer.parseInt(officePort));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String request = "r:" + this.host + "," + this.port;
            out.println(request);
            String response = in.readLine();
            this.number = Integer.parseInt(response);
//            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendRequestSETREADY(String officeHost , String officePort) {
        try (Socket socket = new Socket(officeHost, Integer.parseInt(officePort));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request = "sr:" + this.number;
            out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendRequestSETPUMPIN(String sewagePlantHost, String sewagePlantPort) {
        try (Socket socket = new Socket(sewagePlantHost, Integer.parseInt(sewagePlantPort));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request = "spi:" + this.number + "," + this.allVolume;
            out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setJob(String houseHost, String housePort) {
        sendRequestSETREADY(officeHost, officePort);
        System.out.println("TANKER: Setting job to " + houseHost + ":" + housePort);
        sendRequestGETPUMPOUT(houseHost, housePort);
        System.out.println("TANKER: pumping out from " + houseHost + ":" + housePort);
        sendRequestSETPUMPIN(sewagePlantHost, sewagePlantPort);
        System.out.println("TANKER: pumping in to " + sewagePlantHost + ":" + sewagePlantPort);
        this.allVolume = 0;
    }

//    @Override
//    public int handleRequest(String request) {
//
//        return 0;
//    }
//    public static void main(String[] args) {
//        Tanker tanker = new Tanker("localhost",4000, "localhost", "6000", "localhost", "7000", 30);
//        tanker.startServer();
////        tanker.sendRequestREGISTER();
//    }
}
