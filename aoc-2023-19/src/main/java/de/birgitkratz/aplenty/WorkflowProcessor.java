package de.birgitkratz.aplenty;

import de.birgitkratz.aplenty.model.ConditionTarget;
import de.birgitkratz.aplenty.model.Rating;
import de.birgitkratz.aplenty.model.RatingElement;
import de.birgitkratz.aplenty.model.Workflow;
import de.birgitkratz.aplenty.model.WorkflowAccept;
import de.birgitkratz.aplenty.model.WorkflowCondition;
import de.birgitkratz.aplenty.model.WorkflowName;
import de.birgitkratz.aplenty.model.WorkflowReject;
import de.birgitkratz.aplenty.model.WorkflowsAndRatings;

import java.util.List;
import java.util.Map;

class WorkflowProcessor {
    int process(WorkflowsAndRatings workflowsAndRatings) {
        var workflows = workflowsAndRatings.workflows();
        var ratings = workflowsAndRatings.ratings();

        return processRating(workflows, ratings, new WorkflowName("in"), 0, 0);
    }

    private int processRating(Map<WorkflowName, Workflow> workflows, List<Rating> ratings, WorkflowName workflowName, int ratingIndex, int sum) {
        var rating = ratings.get(ratingIndex);
        var workflow = workflows.get(workflowName);

        ConditionTarget result =  processWorkflow(rating, workflow);

        if (result instanceof WorkflowName name) {
            return processRating(workflows, ratings, name, ratingIndex, sum);
        }

        if (++ratingIndex == ratings.size()) {
            if (result instanceof WorkflowAccept) {
                sum += sumUp(rating);
            }
            return sum;
        }

        if (result instanceof WorkflowAccept) {
            sum += sumUp(rating);
            return processRating(workflows, ratings, new WorkflowName("in"), ratingIndex, sum);
        }
        if (result instanceof WorkflowReject) {
            return processRating(workflows, ratings, new WorkflowName("in"), ratingIndex, sum);
        }
        return sum;
    }

    private ConditionTarget processWorkflow(Rating rating, Workflow workflow) {
        for (int i=0; i < workflow.getElements().size(); i++) {
            var workflowElement = workflow.getElements().get(i);
            if (workflowElement instanceof WorkflowCondition condition && evaluate(condition, rating)) {
                return condition.target();
            }
            if (workflowElement instanceof WorkflowAccept accept) {
                return accept;
            }
            if (workflowElement instanceof WorkflowReject reject) {
                return reject;
            }
            if (workflowElement instanceof WorkflowName name) {
                return name;
            }
        }
        return null;
    }

    private boolean evaluate(WorkflowCondition condition, Rating rating) {
        var ratingValue = condition.rating();
        var category = condition.category();
        var comparator = condition.comparator();

        var element = rating.getElements().stream()
                .filter(ratingElement -> ratingElement.name().equals(category))
                .findFirst();
        return element.map(ratingElement -> switch (comparator) {
            case ">" -> ratingElement.rating() > ratingValue;
            case "<" -> ratingElement.rating() < ratingValue;
            default -> false;
        }).orElse(false);
    }

    private int sumUp(Rating rating) {
        return rating.getElements().stream()
                .map(RatingElement::rating)
                .reduce(0, Integer::sum);
    }
}
