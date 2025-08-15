package org.ace.accounting.system.fire.enumTypes;

public enum Floor {
    CONCRETE("Concrete"),
    WOOD("Wood"),
    GROUND("Ground");

    private final String label;

    Floor(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}