package de.birgitkratz.aplenty.visitor;

import de.birgitkratz.AplentyBaseVisitor;
import de.birgitkratz.AplentyParser;
import de.birgitkratz.aplenty.model.WorkflowsAndRatings;
import de.birgitkratz.aplenty.model.Rating;
import de.birgitkratz.aplenty.model.RatingElement;
import de.birgitkratz.aplenty.model.Workflow;
import de.birgitkratz.aplenty.model.WorkflowAccept;
import de.birgitkratz.aplenty.model.WorkflowCondition;
import de.birgitkratz.aplenty.model.WorkflowName;
import de.birgitkratz.aplenty.model.WorkflowReject;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkflowAndRatingsVisitor extends AplentyBaseVisitor<Boolean> {
    private final WorkflowsAndRatings workflowsAndRatings = new WorkflowsAndRatings(new HashMap<>(), new ArrayList<>());

    private Workflow currentWorkflow;
    private Rating currentRating;

    @Override
    public Boolean visitWorkflow_line(AplentyParser.Workflow_lineContext ctx) {
        currentWorkflow = new Workflow();
        visit(ctx.workflow());
        workflowsAndRatings.workflows().put(currentWorkflow.getName(), currentWorkflow);
        currentWorkflow = null;
        return true;
    }

    @Override
    public Boolean visitWorkflow(AplentyParser.WorkflowContext ctx) {
        var name = ctx.workflow_name().LETTERS().getText();
        currentWorkflow.setName(new WorkflowName(name));
        ctx.workflow_element().forEach(this::visit);
        return true;
    }

    @Override
    public Boolean visitWfe_accept(AplentyParser.Wfe_acceptContext ctx) {
        currentWorkflow.addElement(new WorkflowAccept());
        return true;
    }

    @Override
    public Boolean visitWfe_reject(AplentyParser.Wfe_rejectContext ctx) {
        currentWorkflow.addElement(new WorkflowReject());
        return true;
    }

    @Override
    public Boolean visitWfe_wfname(AplentyParser.Wfe_wfnameContext ctx) {
        currentWorkflow.addElement(new WorkflowName(ctx.workflow_name().LETTERS().getText()));
        return true;
    }

    @Override
    public Boolean visitWfe_condition(AplentyParser.Wfe_conditionContext ctx) {
        var category = ctx.condition().CATEGORY().getText();
        var comparator = ctx.condition().COMPARATOR().getText();
        var rating = Integer.valueOf(ctx.condition().RATING().getText());

        var conditionTargetContext = ctx.condition().condition_target();
        var target = switch (conditionTargetContext) {
            case AplentyParser.Ct_acceptContext ctAcceptContext -> new WorkflowAccept();
            case AplentyParser.Ct_rejectContext ctRejectContext -> new WorkflowReject();
            case AplentyParser.Ct_wfnameContext ctWfnameContext -> new WorkflowName(ctWfnameContext.workflow_name().LETTERS().getText());
            case null, default -> null;
        };
        currentWorkflow.addElement(new WorkflowCondition(category, comparator, rating, target));
        return true;
    }

    @Override
    public Boolean visitRating_line(AplentyParser.Rating_lineContext ctx) {
        currentRating = new Rating();
        visit(ctx.rating());
        workflowsAndRatings.ratings().add(currentRating);
        currentRating = null;
        return true;
    }

    @Override
    public Boolean visitRating(AplentyParser.RatingContext ctx) {
        ctx.rating_element().forEach(this::visit);
        return true;
    }

    @Override
    public Boolean visitRating_element(AplentyParser.Rating_elementContext ctx) {
        var category = ctx.CATEGORY().getText();
        var rating = Integer.valueOf(ctx.RATING().getText());
        currentRating.addElement(new RatingElement(category, rating));
        return true;
    }

    public WorkflowsAndRatings getWorkflowsAndRatings() {
        return workflowsAndRatings;
    }
}
