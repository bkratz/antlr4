package de.birgitkratz.aplenty;

import de.birgitkratz.aplenty.listener.ListenerProcessor;
import de.birgitkratz.aplenty.visitor.VisitorProcessor;

import java.io.IOException;

public class Aplenty {

    public static void main(String[] args) throws IOException {
        String inputFile = null;
        String method = "listener";
        if (args.length > 0) {
            inputFile = args[0];
            if (args.length > 1) {
                method = args[1];
            }
        }
        if (inputFile != null) {
            var parseTreeFromInput = new ParseTreeCreator().createParseTreeFromInput(inputFile);
            if (method.equals("listener")) {
                var workflowsAndRatings = new ListenerProcessor().processParseTree(parseTreeFromInput);
                int ratingSum = new WorkflowProcessor().process(workflowsAndRatings);
                System.out.println(ratingSum);
            } else if (method.equals("visitor")) {
                var workflowAndRatings = new VisitorProcessor().processParseTree(parseTreeFromInput);
                int ratingSum = new WorkflowProcessor().process(workflowAndRatings);
                System.out.println(ratingSum);
            } else {
                System.out.println("Please chose between 'visitor' or 'listener' as second parameter.");
            }
        }
    }
}
