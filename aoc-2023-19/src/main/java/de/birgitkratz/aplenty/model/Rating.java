package de.birgitkratz.aplenty.model;

import java.util.ArrayList;
import java.util.List;

public class Rating {
    private List<RatingElement> elements;

    public List<RatingElement> getElements() {
        return elements;
    }

    public void addElement(RatingElement element) {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        elements.add(element);
    }
}
