package org.ace.accounting.common;

public enum Branch {
    HEAD_OFFICE("Head Office"),
    MANDALAY_BRANCH("Mandalay Branch"),
    MAYANGONE_BRANCH("Mayangone Branch"),
    MAIN("Main");

    private final String label;

    Branch(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

