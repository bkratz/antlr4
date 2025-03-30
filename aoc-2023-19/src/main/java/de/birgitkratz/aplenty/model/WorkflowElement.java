package de.birgitkratz.aplenty.model;

public sealed interface WorkflowElement permits WorkflowCondition, WorkflowName, WorkflowAccept, WorkflowReject{
}
