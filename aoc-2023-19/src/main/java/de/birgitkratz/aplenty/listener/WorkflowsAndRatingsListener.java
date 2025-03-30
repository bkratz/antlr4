package de.birgitkratz.aplenty.listener;

import de.birgitkratz.AplentyBaseListener;
import de.birgitkratz.AplentyParser;
import de.birgitkratz.aplenty.model.ConditionTarget;
import de.birgitkratz.aplenty.model.Rating;
import de.birgitkratz.aplenty.model.RatingElement;
import de.birgitkratz.aplenty.model.Workflow;
import de.birgitkratz.aplenty.model.WorkflowAccept;
import de.birgitkratz.aplenty.model.WorkflowCondition;
import de.birgitkratz.aplenty.model.WorkflowName;
import de.birgitkratz.aplenty.model.WorkflowReject;
import de.birgitkratz.aplenty.model.WorkflowsAndRatings;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkflowsAndRatingsListener extends AplentyBaseListener {

    private final WorkflowsAndRatings workflowsAndRatings = new WorkflowsAndRatings(new HashMap<>(), new ArrayList<>());

    private Workflow currentWorkflow;
    private Rating currentRating;

    @Override
    public void enterWorkflow_line(AplentyParser.Workflow_lineContext ctx) {
        currentWorkflow = new Workflow();
    }

    @Override
    public void exitWorkflow_line(AplentyParser.Workflow_lineContext ctx) {
        workflowsAndRatings.workflows().put(currentWorkflow.getName(), currentWorkflow);
        currentWorkflow = null;
    }

    @Override
    public void enterWorkflow_name(AplentyParser.Workflow_nameContext ctx) {
        if (ctx.getParent() instanceof AplentyParser.WorkflowContext) {
            currentWorkflow.setName(new WorkflowName(ctx.LETTERS().getText()));
        }
    }

    @Override
    public void enterWfe_wfname(AplentyParser.Wfe_wfnameContext ctx) {
        currentWorkflow.addElement(new WorkflowName(ctx.workflow_name().LETTERS().getText()));
    }

    @Override
    public void enterWfe_accept(AplentyParser.Wfe_acceptContext ctx) {
        currentWorkflow.addElement(new WorkflowAccept());
    }

    @Override
    public void enterWfe_reject(AplentyParser.Wfe_rejectContext ctx) {
        currentWorkflow.addElement(new WorkflowReject());
    }

    @Override
    public void enterWfe_condition(AplentyParser.Wfe_conditionContext ctx) {
        var category = ctx.condition().CATEGORY().getText();
        var rating = Integer.valueOf(ctx.condition().RATING().getText());
        var comparator = ctx.condition().COMPARATOR().getText();

        var conditionTargetContext = ctx.condition().condition_target();
        ConditionTarget target = switch (conditionTargetContext) {
            case AplentyParser.Ct_acceptContext ctAcceptContext -> new WorkflowAccept();
            case AplentyParser.Ct_rejectContext ctRejectContext -> new WorkflowReject();
            case AplentyParser.Ct_wfnameContext ctWfnameContext -> new WorkflowName(ctWfnameContext.workflow_name().LETTERS().getText());
            default -> null;
        };
        currentWorkflow.addElement(new WorkflowCondition(category, comparator, rating, target));
    }

    @Override
    public void enterCondition(AplentyParser.ConditionContext ctx) {
        super.enterCondition(ctx);
    }

    @Override
    public void enterRating_line(AplentyParser.Rating_lineContext ctx) {
        currentRating = new Rating();
    }

    @Override
    public void exitRating_line(AplentyParser.Rating_lineContext ctx) {
        workflowsAndRatings.ratings().add(currentRating);
        currentRating = null;
    }

    @Override
    public void enterRating_element(AplentyParser.Rating_elementContext ctx) {
        var category = ctx.CATEGORY().getText();
        var rating = Integer.valueOf(ctx.RATING().getText());

        currentRating.addElement(new RatingElement(category, rating));
    }

    public WorkflowsAndRatings getWorkflowsAndRatings() {
        return workflowsAndRatings;
    }
}
