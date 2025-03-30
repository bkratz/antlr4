package de.birgitkratz.aplenty.listener;

import de.birgitkratz.aplenty.ParseTreeCreator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ListenerProcessorTest {

    @Test
    void testWorkflows() throws IOException {
        var parseTreeCreator = new ParseTreeCreator();
        var parseTreeFromInput = parseTreeCreator.createParseTreeFromInput("aplenty.input");
        var workflowsAndRatings = new ListenerProcessor().processParseTree(parseTreeFromInput);

        assertThat(workflowsAndRatings.getWorkflows()).isNotNull();
        assertThat(workflowsAndRatings.getRatings()).isNotNull();
    }
}