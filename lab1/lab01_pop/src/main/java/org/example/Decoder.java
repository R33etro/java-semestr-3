package org.example;

public class Decoder {
    public static String decoder(String message, char[][] key) {
        int rows = key.length;
        int cols = key[0].length;
        int let1_row = 0;
        int let1_col = 0;
        int let2_row = 0;
        int let2_col = 0;

        int msg_len = message.length();
        char[] msg = message.toCharArray();

        for (int i = 0; i < msg_len; i += 2) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {

                    if (msg[i] == key[j][k]) {
                        let1_row = j;
                        let1_col = k;
                    }
                    if (msg[i + 1] == key[j][k]) {
                        let2_row = j;
                        let2_col = k;
                    }
                }
            }
           //DECODER
//setting pair of letter
//-------------------------
//case same rows
                if (let1_row == let2_row) {
                    //first letter set
                    if (let1_col == 0) {
                        msg[i] = key[let1_row][key[0].length - 1];
                    } else {
                        msg[i] = key[let1_row][let1_col - 1];
                    }
                    //second letter set
                    if (let2_col == 0) {
                        msg[i + 1] = key[let1_row][key[0].length - 1];
                    } else {
                        msg[i + 1] = key[let1_row][let2_col - 1];
                    }
//case same columns
                } else if (let1_col == let2_col) {
                    //first letter set
                    if (let1_row == 0) {
                        msg[i] = key[key.length - 1][let1_col];
                    } else {
                        msg[i] = key[let1_row - 1][let1_col];
                    }
                    //second letter set
                    if (let2_row == 0) {
                        msg[i + 1] = key[key.length - 1][let1_col];
                    } else {
                        msg[i + 1] = key[let2_row - 1][let1_col];
                    }
//case else
                } else {
                    msg[i] = key[let2_row][let1_col];
                    msg[i + 1] = key[let1_row][let2_col];
                }
        }
        return new String(msg);
    }
}
