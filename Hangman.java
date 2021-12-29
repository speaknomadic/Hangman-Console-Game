package com.company.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class Hangman {

    public static void main(String[] args) {
        //   printMessage("Hangman", true, true);
        //  drawHangman(9);
        //  printAvailableLetters("bdxy");
        String d = loadRandomWord("words.txt");
        System.out.println(d);
    }

    static void printMessage(String message, boolean printTop, boolean printBottom) {
        if (printTop) {
            System.out.println("+---------------------------------+");
        }
        System.out.print("|");
        boolean front = true;
        for (int i = message.length(); i < 33; i++) {
            if (front) {
                message = " " + message;
            } else {
                message = message + " ";
            }
            front = ! front;
        }

        System.out.print(message);

        if (printBottom) {
            System.out.println("|");
            System.out.println("+---------------------------------+");
        } else {
            System.out.println("|");
        }
    }

    static void drawHangman(int guessCount) {
        if (guessCount >= 1) {
            printMessage("|", false, false);
        } else {
            printMessage(" ", false, false);
        }
        if (guessCount >= 2) {
            printMessage("|", false, false);
        } else {
            printMessage(" ", false, false);
        }
        if (guessCount >= 3) {
            printMessage("O", false, false);
        } else {
            printMessage(" ", false, false);
        }
        if (guessCount == 4) {
            printMessage("/", false, false);
        }
        if (guessCount == 5) {
            printMessage("/|", false, false);
        }
        if (guessCount >= 6) {
            printMessage("/|\\", false, false);
        } else {
            printMessage(" ", false, false);
        }
        if (guessCount >= 7) {
            printMessage("|", false, false);
        }
        if (guessCount == 8) {
            printMessage("/", false, false);
        }
        if (guessCount >= 9) {
            printMessage("/ \\", false, false);
        } else {
            printMessage(" ", false, false);
        }
    }

    static void printLetters(String input, char from, char to) {
        String s = "";
        for (char i = from; i <= to; i++) {
            if (input.indexOf(i) == - 1) {
                s += i;
            }
            s += " ";
        }
        printMessage(s, false, false);
    }

    static void printAvailableLetters(String taken) {
        printMessage("Available Letters", true, true);
        printLetters(taken, 'a', 'm');
        printLetters(taken, 'n', 'z');
    }

    static boolean printWordCheckWin(String word, String guessed) {
        boolean won = true;
        String s = "";
        if (! word.equals(guessed)) {
            won = false;
            s += "_";
        } else {
            for (int i = 0; i < guessed.length(); i++) {
                s += guessed.charAt(i);
                s += " ";
            }
        }
        printMessage(s, false, false);
        return won;
    }

    static String loadRandomWord(String path) {
        int lineCount = 0;
        String word = "";
        Vector<String> v = new Vector<>();
        BufferedReader objReader = null;
        try {
            String strCurrentLine;

            Path path1 = Paths.get("C:\\Users\\I356255\\IdeaProjects\\INFM011Algorithms\\src\\com\\company\\hangman");
            objReader = Files.newBufferedReader(path1);

            while ((strCurrentLine = objReader.readLine()) != null) {
                v.add(strCurrentLine);
            }
            int randLine = (int) (Math.random() % v.size());
            word = v.elementAt(randLine);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return word;
    }

}
