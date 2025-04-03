package org.example;

public class Checker {
    public static String checker(String message) {
        for (int i = 1; i < message.length(); i++) {
            if (message.charAt(i) == message.charAt(i - 1)) {
                message = message.substring(0, i) + "x" + message.substring(i);
                i--;
            }
        }

        if (message.length()%2 != 0) {message += 'x';}

        return message;
    }
}
