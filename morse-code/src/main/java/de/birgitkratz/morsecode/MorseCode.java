package de.birgitkratz.morsecode;


import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class MorseCode {

    public static void main(String[] args) throws IOException {
        String inputFile = "testinput.txt";
        String method = "listener";
        if (args.length > 0) {
            inputFile = args[0];
            if (args.length > 1) {
                method = args[1];
            }
        }
        if (inputFile != null) {
            var parseTree = new ParseTreeCreator().createParseTreeFromInput(inputFile);
            var walker = new ParseTreeWalker();
            switch (method) {
                case "listener" -> walker.walk(new MorseCodeListener(), parseTree);
                case "visitor" -> new MorseCodeVisitor().visit(parseTree);
                case "sound" -> walker.walk(new MorseCodeSoundListener(), parseTree);
                default -> System.out.println("Please chose between 'visitor' or 'listener' as second parameter.");
            }
        }
    }
}
