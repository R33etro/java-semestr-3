package org.kulejp.house;

import java.io.*;
import java.net.*;


public class House implements IHouse {
    private final String host;
    private final int port;
     int maxVolume;
     int currentVolume;
    private ServerSocket serverSocket;

    public House(String host, int port, int maxVolume) {
        this.host = host;
        this.port = port;
        this.maxVolume = maxVolume;
    }

    public void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("House server running on port " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClient(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // request from tanker: gp(int)
    private void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String request = in.readLine();
            if (request != null && request.startsWith("gp:")) {
                int value = Integer.parseInt(request.substring(3));
                out.println(getPumpOut(value));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    // send request to Office
    public void sendRequestORDER(String officeHost, String officePort) {
        try (Socket socket = new Socket(officeHost, Integer.parseInt(officePort));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String request = "o:" + this.host + ","+ this.port+"\n";
            out.println(request);
            String response = in.readLine();
            System.out.println("HOUSE: response from tanker: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getPumpOut(int maxVolOfTanker) {
        System.out.println("HOUSE: volume before: " + this.currentVolume);

        if(this.currentVolume >= maxVolOfTanker) {
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


//    public static void main(String[] args) {
//        House house = new House("localhost",5000, 60);
//        house.startServer();
//        house.currentVolume = 50;
//        house.sendRequestORDER("localhost", "6000");
//    }
}
