package org.ace.accounting.common;

import java.util.Arrays;
import java.util.List;

public enum PaymentType {
    SEMI_ANNUAL("SEMI-ANNUAL"),
    QUARTER("QUARTER"),
    LUMPSUM("LUMPSUM"),
    MONTHLY("MONTHLY");

    private final String label;

    private PaymentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<PaymentType> getAllTypes() {
        return Arrays.asList(values());
    }
}

