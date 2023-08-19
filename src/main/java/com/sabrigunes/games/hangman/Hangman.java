package com.sabrigunes.games.hangman;

import com.sabrigunes.utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Hangman {

    private static int step;
    private static int score;
    private static final DatabaseUtil database;
    private static final Scanner scanner;
    private static char[] characters;
    private static boolean[] status;
    private static ArrayList<Character> guesses;

    private static int failedGuess;

    public static void run() {
        startGame();
        playGame();
    }

    static {
        database = new DatabaseUtil("postgres", "Aa10203040**", "test");
        scanner = new Scanner(System.in);
    }

    private static void startGame() {
        System.out.println(HangmanPicGenerator.HANGMAN_WORD);
        System.out.println("Hangman (adam asmaca) oyununa Hoşgeldiniz.\nİlk tur başlatılıyor.\n");
    }

    private static void playGame() {
        for (; ; ) {
            playTurn();
            if (!isWantKeepPlaying()){
                System.out.println("Görüşmek üzere...");
                break;
            }
        }
    }

    private static boolean isWantKeepPlaying() {
        System.out.println("Oynamaya devam etmek istiyor musunuz? (E/H)");
        String input = scanner.nextLine().toUpperCase().trim();
        boolean result = false;
        switch (input) {
            case "EVET":
            case "E":
            case "YES":
            case "Y":
                result = true;
                break;
            case "HAYIR":
            case "H":
            case "NO":
            case "N":
                break;
            default:
                System.out.println("Yanlış giriş yaptınız.");
                isWantKeepPlaying();
        }
        return result;
    }

    private static void playTurn() {
        String word = getWord();
        prepareTurn(word);

        for (; ; ) {
            var guess = takeGuess();
            processGuess(guess);
            System.out.println(printWord());
            if (isWordKnown()) {
                System.out.printf("Tebrikler!%nKelimeyi başarıyla bildin.");
                break;
            }
            if (failedGuess > 5) {
                System.out.println("Üzgünüm, kaybettin.%nKelimeyi tahmin etmeyi başaramadın.");
                break;
            }
        }
    }

    private static boolean isWordKnown() {
        for (boolean b : status)
            if (!b)
                return false;
        return true;
    }

    private static void processGuess(char guess) {
        int flag = 0;
        for (int i = 0; i < characters.length; ++i) {
            if (characters[i] == guess) {
                status[i] = true;
                ++flag;
            }
        }
        if (flag != 0)
            System.out.printf("Tahmin başarılı. %d harf açıldı.%n", flag);

        else {
            ++failedGuess;
            System.out.printf("Üzgünüm. Tahmininiz hatalı.%n");
            System.out.println(HangmanPicGenerator.getPic(failedGuess));
        }


    }

    private static char takeGuess() {
        for (; ; ) {
            System.out.print("Harf tahmininde bulunun: ");
            String guess = scanner.nextLine().toUpperCase();
            if (guess.length() != 1 || !Character.isAlphabetic(Character.valueOf(guess.charAt(0)))) {
                System.out.println("Hatalı giriş yaptınız. Lütfen bir harf girin.");
                continue;
            }
            if (guesses.contains(Character.valueOf(guess.charAt(0)))) {
                System.out.println("Zaten bu girişi daha önce yaptınız. Lütfen farklı bir harf girin.");
                continue;
            }
            guesses.add(Character.valueOf(guess.charAt(0)));
            return guess.charAt(0);
        }
    }

    private static String getWord() {
        try {
            var data = database.fetchData("SELECT * FROM hangman_words ORDER BY random() LIMIT 1;");
            if (data.next())
                return data.getString(2);
            return getWord();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    private static void prepareTurn(String word) {
        characters = new char[word.length()];
        status = new boolean[word.length()];
        guesses = new ArrayList<>();
        failedGuess = 0;

        word = word.toUpperCase();
        for (int i = 0; i < word.length(); ++i)
            characters[i] = word.charAt(i);

        System.out.println(printWord());

    }

    private static String printWord() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < status.length; ++i) {
            stringBuilder.append(status[i] ? characters[i] + " " : "_" + " ");
        }
        return stringBuilder.toString();
    }


}
