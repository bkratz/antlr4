package de.birgitkratz.aplenty.model;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private WorkflowName name;
    private List<WorkflowElement> elements;

    public WorkflowName getName() {
        return name;
    }

    public void setName(WorkflowName name) {
        this.name = name;
    }

    public List<WorkflowElement> getElements() {
        return elements;
    }

    public void addElement(WorkflowElement element) {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        elements.add(element);
    }
}
