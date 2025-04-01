package de.birgitkratz.aplenty;

import de.birgitkratz.AplentyLexer;
import de.birgitkratz.AplentyParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ParseTreeCreator {
    public ParseTree createParseTreeFromInput(String inputFile) throws IOException {
        var path = Paths.get(inputFile);
        try(var in = new FileInputStream(path.toFile())) {
            var input = CharStreams.fromStream(in);
            var lexer = new AplentyLexer(input);
            var tokens = new CommonTokenStream(lexer);
            var parser = new AplentyParser(tokens);
            return parser.aplenty();
        }
    }

}
