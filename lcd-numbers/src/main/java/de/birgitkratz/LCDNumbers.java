package de.birgitkratz;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class LCDNumbers extends LCDBaseListener {
    private int numberCount = 0;
    private String[] currentNumbers;

    public static void main(String[] args) throws IOException {
        String inputFile = "testinput.txt";

        LCDParser parser;
        var path = Paths.get(inputFile);
        try (var in = new FileInputStream(path.toFile())) {
            var input = CharStreams.fromStream(in);
            var lexer = new LCDLexer(input);
            var tokens = new CommonTokenStream(lexer);
            parser = new LCDParser(tokens);
        }
        var walker = new ParseTreeWalker();
        walker.walk(new LCDNumbers(), parser.file());
    }

    private void initCurrentNumbers() {
        currentNumbers = new String[]{"", "", "", "", "", "", "", "", ""};
    }

    @Override
    public void enterLcd_numbers(LCDParser.Lcd_numbersContext ctx) {
        initCurrentNumbers();
    }

    @Override
    public void exitLcd_numbers(LCDParser.Lcd_numbersContext ctx) {
        System.out.print("Account Number: ");
        Arrays.stream(currentNumbers)
                .map(this::convertToDecimal)
                .forEach(System.out::print);
        System.out.println();
        initCurrentNumbers();
    }

    @Override
    public void exitLcd_number_line(LCDParser.Lcd_number_lineContext ctx) {
        numberCount = 0;
    }

    @Override
    public void exitNumber_parts(LCDParser.Number_partsContext ctx) {
        var lcdNumberPart = ctx.NUMBER_PART().getText();
        currentNumbers[numberCount] += lcdNumberPart;
        numberCount++;
    }

    private int convertToDecimal(String lcdNumber) {
        return switch (lcdNumber) {
            case " _ | ||_|" -> 0;
            case "     |  |" -> 1;
            case " _  _||_ " -> 2;
            case " _  _| _|" -> 3;
            case "   |_|  |" -> 4;
            case " _ |_  _|" -> 5;
            case " _ |_ |_|" -> 6;
            case " _   |  |" -> 7;
            case " _ |_||_|" -> 8;
            case " _ |_| _|" -> 9;
            default -> -1;
        };
    }
}
