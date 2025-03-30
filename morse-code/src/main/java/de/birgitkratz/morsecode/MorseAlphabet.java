package de.birgitkratz.morsecode;

import java.util.Map;

import static java.util.Map.entry;

class MorseAlphabet {

    static Map<String, String> morseToLetter = Map.ofEntries(
            entry(".-", "A"),
            entry("-...", "B"),
            entry("-.-.", "C"),
            entry("-..", "D"),
            entry(".", "E"),
            entry("..-.", "F"),
            entry("--.", "G"),
            entry("....", "H"),
            entry("..", "I"),
            entry(".---", "J"),
            entry("-.-", "K"),
            entry(".-..", "L"),
            entry("--", "M"),
            entry("-.", "N"),
            entry("---", "O"),
            entry(".--.", "P"),
            entry("--.-", "Q"),
            entry(".-.", "R"),
            entry("...", "S"),
            entry("-", "T"),
            entry("..-", "U"),
            entry("...-", "V"),
            entry(".--", "W"),
            entry("-..-", "X"),
            entry("-.--", "Y"),
            entry("--..", "Z"),
            entry(".-.-.-", "."),
            entry("..--..", "?"),
            entry("-.-.--", "!")
    );

    private MorseAlphabet() {
        // do nothing
    }
}
