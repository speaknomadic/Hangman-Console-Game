package com.company.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Hangman {

    public static void main(String[] args) {

        StringBuilder guesses = new StringBuilder();
        String wordToGuess = loadRandomWord("C:\\Users\\I356255\\Desktop\\nbu\\words.txt");
        int tries = 0;
        boolean win;

        Scanner scanner = new Scanner(System.in);
        do {

            printMessage("HangMan", true, true);
            drawHangman(tries);
            printAvailableLetters(guesses.toString());
            printMessage("Guess the Word", true, true);

            win = printWordCheckWin(wordToGuess, guesses.toString());

            if (win)
                break;
            char x;
            System.out.println(">");

            x = scanner.next().charAt(0);
            if (guesses.indexOf(String.valueOf(x)) == - 1) {
                guesses.append(x);
            }
            tries = triesLeft(wordToGuess, guesses.toString());
        } while (tries < 10);
        if (win) {
            printMessage("You WON!", true, true);
        } else {
            printMessage(" Game over!", true, true);
            printMessage(" The word is " + wordToGuess, true, true);
        }
        scanner.close();
        guesses.setLength(0);
    }

    /**
     * printMessage() prints the game console box and positions any text in the middle.
     * @param message
     * @param printTop
     * @param printBottom
     */
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

    /**
     * drawHangman() draws the hangman, and all empty spaces and rows
     * @param guessCount
     */
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

    /**
     * printLetters() prints the menu of Available letters, which are left for guessing, from char to char, avoiding the letters which are in the input string.
     * In their place a space is printed.
     *
     * @param input
     * @param from
     * @param to
     */
    static void printLetters(String input, char from, char to) {
        StringBuilder s = new StringBuilder();
        for (char i = from; i <= to; i++) {
            if (input.indexOf(i) == - 1) {
                s.append(i);
            }
            s.append(" ");
        }
        printMessage(s.toString(), false, false);
        s.setLength(0);
    }

    /**
     * printAvailableLetters() prints all the letters available for play, which have not been tried yet.
     * If a letter has already been tried, an empty space is printed in its place.
     * @param taken
     */
    static void printAvailableLetters(String taken) {
        printMessage("Available Letters", true, true);
        printLetters(taken, 'a', 'm');
        printLetters(taken, 'n', 'z');
    }

    /**
     * printWordCheckWin() checks if any of the letters in the word for guessing are in the guessed word.
     * If any letter is correct, it is printed in its place, if any is missing an underscore is printed in its place.
     * If all letters in the word for guessing are in the guessed word the won equals to true, there is winner.
     * @param word
     * @param guessed
     * @return
     */
    static boolean printWordCheckWin(String word, String guessed) {
        boolean won = true;
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (guessed.lastIndexOf(word.charAt(i)) == - 1) {
                won = false;
                s.append("_");
            } else {
                s.append(word.charAt(i));
                s.append(" ");
            }
        }
        printMessage(s.toString(), false, false);
        s.setLength(0);
        return won;
    }

    /**
     * loadRandomWord(). Reads all the words from a text file, and adds them to a Vector.
     * Loads word randomly.
     * @param path
     * @return word
     *
     */
    static String loadRandomWord(String path) {
        int lineCount = 0;
        String word = "";
        Vector<String> v = new Vector<>(7000);
        BufferedReader objReader = null;
        try {
            String strCurrentLine;
            objReader = Files.newBufferedReader(Paths.get(path));

            while ((strCurrentLine = objReader.readLine()) != null) {
                v.add(strCurrentLine);
            }
            Random rand = new Random();
            int min = 0;
            int max = v.size();
            int randLine = (int) Math.floor(Math.random() * (max - min + 1) + min);

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

    /**
     * triesLeft() counts the number of letters which are incorrect.
     * @param word
     * @param guessedWord
     * @return
     */
    static int triesLeft(String word, String guessedWord) {
        int error = 0;
        for (int i = 0; i < guessedWord.length(); i++) {
            if (word.indexOf(guessedWord.charAt(i)) == - 1) {
                error++;
            }
        }
        return error;
    }
}
