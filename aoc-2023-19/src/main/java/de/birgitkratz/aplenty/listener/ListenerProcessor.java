package de.birgitkratz.aplenty.listener;

import de.birgitkratz.aplenty.model.WorkflowsAndRatings;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class ListenerProcessor {
    public WorkflowsAndRatings processParseTree(ParseTree parseTree) throws IOException {
        var walker = new ParseTreeWalker();

        var listenerResult = new WorkflowsAndRatingsListener();
        walker.walk(listenerResult, parseTree);
        return listenerResult.getWorkflowsAndRatings();
    }
}
