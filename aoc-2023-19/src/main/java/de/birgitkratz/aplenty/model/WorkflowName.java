package de.birgitkratz.aplenty.model;

public record WorkflowName(String name) implements WorkflowElement, ConditionTarget {}
