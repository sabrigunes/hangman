package com.sabrigunes.games.hangman;

import java.util.Arrays;

public final class HangmanPicGenerator {
    private static final String[] HANGMAN_PICS = new String[]{
            "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
            "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n========",
            "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n========",
            "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n========",
            "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========",
    };

    public static final String HANGMAN_WORD = "██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███╗   ███╗ █████╗ ███╗   ██╗\n██║  ██║██╔══██╗████╗  ██║██╔════╝ ████╗ ████║██╔══██╗████╗  ██║\n███████║███████║██╔██╗ ██║██║  ███╗██╔████╔██║███████║██╔██╗ ██║\n██╔══██║██╔══██║██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══██║██║╚██╗██║\n██║  ██║██║  ██║██║ ╚████║╚██████╔╝██║ ╚═╝ ██║██║  ██║██║ ╚████║\n╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝\n\n\n";

    public static String getPic(int step){
        return HANGMAN_PICS[Math.min(step, 6)];
    }
}
