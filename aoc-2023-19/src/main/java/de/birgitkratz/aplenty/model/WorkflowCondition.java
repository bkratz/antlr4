package de.birgitkratz.aplenty.model;

public record WorkflowCondition(String category, String comparator, Integer rating, ConditionTarget target) implements WorkflowElement {
}
