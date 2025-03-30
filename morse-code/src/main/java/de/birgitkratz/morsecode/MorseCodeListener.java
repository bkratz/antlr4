package de.birgitkratz.morsecode;

import de.birgitkratz.MorseCodeBaseListener;
import de.birgitkratz.MorseCodeParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.birgitkratz.morsecode.MorseAlphabet.morseToLetter;

public class MorseCodeListener extends MorseCodeBaseListener {

    List<String> sentences = new ArrayList<>();
    List<String> words = new ArrayList<>();


    @Override
    public void exitFile(MorseCodeParser.FileContext ctx) {
        sentences.forEach(System.out::println);
    }

    @Override
    public void exitLine(MorseCodeParser.LineContext ctx) {
        sentences.add(String.join(" ", words));
        words.clear();
    }

    @Override
    public void exitWord(MorseCodeParser.WordContext ctx) {
        var word = ctx.LETTER().stream()
                .map(morseLetter -> morseToLetter.getOrDefault(morseLetter.getText(), ""))
                .collect(Collectors.joining());

        words.add(word);
    }
}

