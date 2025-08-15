package org.ace.accounting.system.fire.enumTypes;

public enum Wall {
    BRICK("Brick"),
    BRICK_METAL("Brick + Metal"),
    BRICK_TIMBER("Brick + Timber"),
    AC_SHEET("AC Sheet"),
    METAL_SHEET("Metal Sheet");

    private final String label;

    Wall(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}