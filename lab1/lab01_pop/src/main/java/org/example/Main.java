package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        char[][] key = {
                {'a','b','c','d','e'},
                {'f','g','h','i','j'},
                {'k','l','m','n','o'},
                {'p','r','s','t','u'},
                {'v','w','x','y','z'}};

        String codedMessage = "";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pass a message without character 'q'");
        String message;
        if(!scanner.hasNextLine()) {
            System.out.println("something went wrong");
        } else {
            message = scanner.nextLine();
            codedMessage = Coder.coder(Checker.checker(message), key);
        }
        System.out.println("coded msg: " + codedMessage);
        System.out.println("decoded msg: " + Decoder.decoder(codedMessage, key));
    }
}