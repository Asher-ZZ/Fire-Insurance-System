package org.ace.accounting.common;

public enum BuildingClassType {
    FIRST_CLASS("FIRST CLASS"),
    SECOND_CLASS("SECOND CLASS"),
    THIRD_CLASS("THIRD CLASS"),
    FOURTH_CLASS("FOURTH CLASS");

    private final String label;

    BuildingClassType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
