package de.birgitkratz.aplenty;

import de.birgitkratz.aplenty.listener.ListenerProcessor;
import de.birgitkratz.aplenty.listener.WorkflowsAndRatingsListener;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowProcessorTest {
    @Test
    void processWithListener() throws IOException {
        var parseTreeCreator = new ParseTreeCreator();
        var parseTreeFromInput = parseTreeCreator.createParseTreeFromInput("aplenty.input");
        var workflowsAndRatings = new ListenerProcessor().processParseTree(parseTreeFromInput);

        var workflowProcessor = new WorkflowProcessor();
        var result = workflowProcessor.process(workflowsAndRatings);

        assertThat(result).isEqualTo(19114);
    }
}