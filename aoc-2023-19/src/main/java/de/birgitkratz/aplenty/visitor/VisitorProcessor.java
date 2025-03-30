package de.birgitkratz.aplenty.visitor;

import de.birgitkratz.aplenty.model.WorkflowsAndRatings;
import org.antlr.v4.runtime.tree.ParseTree;

public class VisitorProcessor {
    public WorkflowsAndRatings processParseTree(ParseTree parseTreeFromInput) {
        var visitorResult = new WorkflowAndRatingsVisitor();
        parseTreeFromInput.accept(visitorResult);

        return visitorResult.getWorkflowsAndRatings();
    }
}
