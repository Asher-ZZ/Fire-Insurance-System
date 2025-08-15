package org.ace.accounting.system.fire.enumTypes;

public enum Roofing {
    ALUZINC("Aluzinc"),
    CLAY_BRICK_TILE("Clay/Brick Tile"),
    AC_SHEET("AC Sheet"),
    METAL_SHEET("Metal Sheet"),
    AMCAN("Amcan");

    private final String label;

    Roofing(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

