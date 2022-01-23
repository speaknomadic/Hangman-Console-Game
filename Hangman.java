package com.company.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Hangman {

    public static void main(String[] args) throws Exception {

        StringBuilder lettersUsed = new StringBuilder();
        String wordToGuess = loadRandomWord(readWordsFromFile("C:\\Users\\I356255\\Desktop\\nbu\\words.txt"));
        if (wordToGuess == null || wordToGuess.isBlank())
            throw new Exception("Invalid word");
        int attempts = 0;
        boolean hasWon;
        Scanner scanner = new Scanner(System.in);
        do {
            printMessage("HangMan", true, true);
            drawHangman(attempts);
            printAvailableLetters(lettersUsed.toString());
            printMessage("Guess the Word", true, true);
            hasWon = printWordCheckWin(wordToGuess, lettersUsed.toString());

            if (hasWon) {
                break;
            }

            char x = getUserInput(scanner);

            if (lettersUsed.indexOf(String.valueOf(x)) == - 1) {
                lettersUsed.append(x);
            }
            attempts = triesLeft(wordToGuess, lettersUsed.toString());
        } while (attempts < 10);
        if (hasWon) {
            printMessage("You WON!", true, true);
        } else {
            printMessage(" Game over!", true, true);
            printMessage(" The word is " + wordToGuess, true, true);
        }
        scanner.close();
    }

    private static char getUserInput(Scanner scanner) {
        char x;
        do {
            System.out.println(">");
            x = scanner.next().charAt(0);
        }
        while (! isBasicLetter(x));
        return x;
    }

    /**
     * printMessage() prints the game console box and positions any text in the middle.
     *
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
     */
    static void printLetters(String lettersUsed, char from, char to) {
        StringBuilder s = new StringBuilder(33);
        for (char i = from; i <= to; i++) {
            if (lettersUsed.indexOf(i) == - 1) {
                s.append(i);
            }
            s.append(" ");
        }
        printMessage(s.toString(), false, false);
    }

    /**
     * printAvailableLetters() prints all the letters available for play, which have not been tried yet.
     * If a letter has already been tried, an empty space is printed in its place.
     */
    static void printAvailableLetters(String lettersUsed) {
        printMessage("Available Letters", true, true);
        printLetters(lettersUsed, 'a', 'm');
        printLetters(lettersUsed, 'n', 'z');
    }

    /**
     * printWordCheckWin() checks if any of the letters in the word for guessing are in the guessed word.
     * If any letter is correct, it is printed in its place, if any is missing an underscore is printed in its place.
     * If all letters in the word for guessing are in the guessed word the won equals to true, there is winner.
     * @param word
     * @param usedLetters
     * @return
     */
    static boolean printWordCheckWin(String word, String usedLetters) {
        boolean won = true;
        StringBuilder s = new StringBuilder(33);

        for (int i = 0; i < word.length(); i++) {
            if (usedLetters.indexOf(word.charAt(i)) == - 1) {
                won = false;
                s.append("_");
            } else {
                s.append(word.charAt(i));
                s.append(" ");
            }
        }
        printMessage(s.toString(), false, false);
        return won;
    }

    /**
     * loadRandomWord().
     * Loads word randomly.
     * @param  vector
     * @return word
     *
     */
    static String loadRandomWord(Vector<String> vector) throws Exception {
        if (vector.size() == 0) {
            throw new Exception("Invalid input");
        }
        Random rand = new Random();
        int randLine = rand.nextInt(vector.size());
        String word = vector.elementAt(randLine);
        return word;
    }

    /**
     * readWordsFromFile(). Reads all the words from a text file, and adds them to a Vector.
     *
     * @param path
     * @return vector
     *
     */
    static Vector<String> readWordsFromFile(String path) {

        Vector<String> vector = new Vector<>(69900);
        BufferedReader objReader = null;
        try {
            String strCurrentLine;
            objReader = Files.newBufferedReader(Paths.get(path));

            while ((strCurrentLine = objReader.readLine()) != null) {
                vector.add(strCurrentLine);
            }

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
        return vector;
    }

    /**
     * triesLeft() counts the number of letters which are incorrect.
     * @param word
     * @param guessedLetters
     * @return
     */
    static int triesLeft(String word, String guessedLetters) {
        int error = 0;
        for (int i = 0; i < guessedLetters.length(); i++) {
            if (word.indexOf(guessedLetters.charAt(i)) == - 1) {
                error++;
            }
        }
        return error;
    }

    static boolean isBasicLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }
}
