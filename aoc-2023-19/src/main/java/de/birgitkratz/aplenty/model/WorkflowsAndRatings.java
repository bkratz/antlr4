package de.birgitkratz.aplenty.model;

import java.util.List;
import java.util.Map;

public record WorkflowsAndRatings(Map<WorkflowName, Workflow> workflows, List<Rating> ratings) {}
