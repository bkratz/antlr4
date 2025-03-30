package de.birgitkratz.morsecode;

import de.birgitkratz.MorseCodeBaseVisitor;
import de.birgitkratz.MorseCodeParser;

import java.util.stream.Collectors;

import static de.birgitkratz.morsecode.MorseAlphabet.morseToLetter;

public class MorseCodeVisitor extends MorseCodeBaseVisitor<String> {
    @Override
    public String visitLine(MorseCodeParser.LineContext ctx) {
        var sentence = ctx.word().stream()
                .map(this::visitWord)
                .collect(Collectors.joining(" "));
        System.out.println(sentence);
        return null;
    }

    @Override
    public String visitWord(MorseCodeParser.WordContext ctx) {
        return ctx.LETTER().stream()
                .map(morseLetter -> morseToLetter.getOrDefault(morseLetter.getText(), ""))
                .collect(Collectors.joining());
    }
}
